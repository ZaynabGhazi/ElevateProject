package edu.brynmawr.cmsc353.elevateproject.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import edu.brynmawr.cmsc353.elevateproject.R;
import edu.brynmawr.cmsc353.elevateproject.adapters.CommentAdapter;
import edu.brynmawr.cmsc353.elevateproject.adapters.QuestionAdapter;
import edu.brynmawr.cmsc353.elevateproject.models.CommentModel;
import edu.brynmawr.cmsc353.elevateproject.models.QuestionModel;

import static android.app.Activity.RESULT_OK;

public class QuestionAndAnswerFragment extends Fragment {
    RecyclerView questionFeedRecyclerView;
    RecyclerView commentFeedRecyclerView;
    String currentUserID;
    String currentUserName;
    List<String> questionIds = new ArrayList<>();
    int SELECT_PICTURE = 200;  // constant to compare the activity result code
    String imagePath = "";
    FloatingActionButton addQuestionButton;
    Bitmap uploadedImage;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.newsfeed_view, null);
        currentUserID = getArguments().getString("userId");
        currentUserName = getArguments().getString("username");
        addQuestionButton = (FloatingActionButton) root.findViewById(R.id.add_question_button);
        addQuestionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onAddQuestionButtonClick(v);
            }
        });
        return root;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        questionFeedRecyclerView = getView().findViewById(R.id.simple_recyclerview);
        new RetrieveFeedTask().execute();
    }

    public void onCommentSectionClick(List<CommentModel> commentModels, String questionId) {
        Dialog dialog = new Dialog(getActivity());
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);

        FloatingActionButton closeButton;
        dialog.setContentView(R.layout.comments_view);
        commentFeedRecyclerView = dialog.findViewById(R.id.comments_recycler_view);
        commentFeedRecyclerView.setHasFixedSize(true);
        commentFeedRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CommentAdapter adapter = new CommentAdapter(commentModels);
        commentFeedRecyclerView.setAdapter(adapter);
        closeButton = dialog.findViewById(R.id.close_comment_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        EditText commentText = dialog.findViewById(R.id.comment_content);
        ImageButton btn = dialog.findViewById(R.id.send_comment_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                new SubmitCommentTask().execute(commentText.getText().toString(), questionId);
            }
        });
    }

    public void onAddQuestionButtonClick(View v) {
        // show the pop up window to add question
        Dialog dialog = new Dialog(getActivity());
        dialog.show();
        dialog.setContentView(R.layout.post_question_view);
        ImageButton closeButton = dialog.findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        EditText titleText = dialog.findViewById(R.id.title_text);
        EditText questionText = dialog.findViewById(R.id.question_text);
        ImageButton uploadQuestionBtn = dialog.findViewById(R.id.upload_question_button);
        Button uploadImageBtn = dialog.findViewById(R.id.upload_image_button);
        uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
        uploadQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String title = titleText.getText().toString();
                String question = questionText.getText().toString();
                new PostQuestionTask().execute(title, question);
            }
        });
    }

    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the uri of the image from data
                Uri selectedImageUri = data.getData();
                uploadedImage = loadFromUri(selectedImageUri);
                Toast.makeText(getActivity(), "File " + selectedImageUri.getPath() + " selected!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class RetrieveFeedTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection connection;
            try {
                URL url = new URL("http://10.0.2.2:3000/api/question/questions");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    connection.disconnect();
                    return response.toString();
                } else
                    System.out.println(responseCode);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                System.out.println("URL not valid!");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Cannot open connection!");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            List<QuestionModel> questionModels = new ArrayList<>();

            try {
                JSONArray jsonArray = new JSONArray(new JSONObject(s).get("data").toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject currentQuestion = jsonArray.getJSONObject(i);
                    String title = currentQuestion.get("title").toString();
                    String content = currentQuestion.get("content").toString();
                    String date = currentQuestion.get("createdAt").toString();
                    String username = currentQuestion.get("creatorName").toString();
                    String commentString = currentQuestion.get("_comments").toString();
                    commentString = commentString.replaceAll("\\[", "").replaceAll("\\]", "");
                    commentString = "[" + commentString + "]";
                    JSONArray commentArray = new JSONArray(commentString);
                    int numComments = commentArray.length();
                    Bitmap image = null;
                    if (currentQuestion.has("image")) {
                        String encodedImage = currentQuestion.get("image").toString();
                        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                        image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    }
                    QuestionModel questionModel = new QuestionModel(username, title, content, 0, numComments, new int[1], date, image);
                    questionModels.add(questionModel);
                    String id = jsonArray.getJSONObject(i).get("_id").toString();
                    questionIds.add(id);
                }
                QuestionAdapter adapter = new QuestionAdapter(questionModels, new QuestionAdapter.ClickCallback() {
                    @Override
                    public void onShowCommentClick(int pos) {
                        System.out.println("Position of this view is: " + pos);
                        System.out.println("The id of this question is: " + questionIds.get(pos));
                        new GetCommentsTask().execute(questionIds.get(pos));
                    }
                });
                questionFeedRecyclerView.setHasFixedSize(true);
                questionFeedRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                questionFeedRecyclerView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class PostQuestionTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String title = strings[0];
            String content = strings[1];
            String data = null;
            HttpURLConnection connection;
            InputStream is = null;
            if (uploadedImage == null) {
                JSONObject j = new JSONObject();
                try {
                    j.put("title", title);
                    j.put("content", content);
                    j.put("userId", currentUserID);
                    j.put("userName", currentUserName);
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Cannot parse JSon!");
                    return null;
                }
                data = j.toString();
            } else {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                uploadedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                JSONObject j = new JSONObject();
                try {
                    j.put("title", title);
                    j.put("content", content);
                    j.put("userId", currentUserID);
                    j.put("userName", currentUserName);
                    j.put("image", encodedImage);
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("Cannot parse JSon!");
                    return null;
                }
                data = j.toString();
            }
            URL url;
            try {
                url = new URL("http://10.0.2.2:3000/api/question/post_question");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Content-Length", Integer.toString(data.getBytes().length));
                connection.setRequestProperty("Content-Language", "en-US");
                connection.setUseCaches(false);
                connection.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(data);
                wr.close();
                try {
                    is = connection.getInputStream();
                } catch (IOException ioe) {
                    if (connection instanceof HttpURLConnection) {
                        HttpURLConnection httpConn = connection;
                        int statusCode = httpConn.getResponseCode();
                        if (statusCode != 200) {
                            is = httpConn.getErrorStream();
                        }
                    }
                }
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
                String line;
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                rd.close();
                connection.disconnect();
                return response.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                System.out.println("URL not valid!");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Cannot open connection!");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            uploadedImage = null;
            Toast.makeText(getActivity(), "Question uploaded! Please go back to the q&a feed!", Toast.LENGTH_LONG).show();
            new RetrieveFeedTask().execute();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetCommentsTask extends AsyncTask<String, String, String> {
        String id;

        @Override
        protected String doInBackground(String... strings) {
            id = strings[0];
            HttpURLConnection connection;
            try {
                URL url = new URL("http://10.0.2.2:3000/api/question/getById?id=" + id);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    connection.disconnect();
                    return response.toString();
                } else
                    System.out.println(responseCode);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                System.out.println("URL not valid!");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Cannot open connection!");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            List<CommentModel> commentModels = new ArrayList<>();
            try {
                String dataString = new JSONObject(s).get("data").toString().replaceAll("\\[", "").replaceAll("\\]", "");
                dataString = "[" + dataString + "]";
                JSONArray jsonArray = new JSONArray(dataString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    String content = jsonArray.getJSONObject(i).get("content").toString();
                    String commentId = jsonArray.getJSONObject(i).get("commentId").toString();
                    String date = jsonArray.getJSONObject(i).get("createdAt").toString();
                    String username = jsonArray.getJSONObject(i).get("creatorName").toString();
                    CommentModel commentModel = new CommentModel(username, commentId, content, date);
                    commentModels.add(commentModel);
                }
                onCommentSectionClick(commentModels, id);
            } catch (JSONException e) {
                e.printStackTrace();
                onCommentSectionClick(commentModels, id);
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class SubmitCommentTask extends AsyncTask<String, String, String> {
        String questionId;

        @Override
        protected String doInBackground(String... strings) {
            String comment = strings[0];
            questionId = strings[1];
            HttpURLConnection connection;
            InputStream is = null;
            try {
                String data = "{\"text\":\"" + comment + "\",\"questionId\":\"" + questionId + "\",\"userId\":\"" + currentUserID + "\",\"creatorName\":\"" + currentUserName + "\"}";
                System.out.println(data);
                URL url = new URL("http://10.0.2.2:3000/api/question/post_comment");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Content-Length", Integer.toString(data.getBytes().length));
                connection.setRequestProperty("Content-Language", "en-US");
                connection.setUseCaches(false);
                connection.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(data);
                wr.close();
                try {
                    is = connection.getInputStream();
                } catch (IOException ioe) {
                    if (connection instanceof HttpURLConnection) {
                        HttpURLConnection httpConn = connection;
                        int statusCode = httpConn.getResponseCode();
                        if (statusCode != 200) {
                            is = httpConn.getErrorStream();
                        }
                    }
                }
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
                String line;
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                rd.close();
                connection.disconnect();
                return response.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                System.out.println("URL not valid!");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Cannot open connection!");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getActivity(), "Comment uploaded! Please refresh feed to see your comment!", Toast.LENGTH_LONG).show();
            new RetrieveFeedTask().execute();
        }
    }

    private Bitmap loadFromUri(Uri photoUri) {
        Bitmap image = null;
        try {
            // check version of Android on device
            if(Build.VERSION.SDK_INT > 27){
                // on newer versions of Android, use the new decodeBitmap method
                ImageDecoder.Source source = ImageDecoder.createSource(getActivity().getContentResolver(), photoUri);
                image = ImageDecoder.decodeBitmap(source);
            } else {
                // support older versions of Android by using getBitmap
                image = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoUri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
