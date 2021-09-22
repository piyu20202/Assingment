package piyu.assign.com.ajm;




import android.app.Activity;
import android.content.Context;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    EditText search_edit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    search_edit =(EditText)findViewById(R.id.search_edit);


    search_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_DONE) {

                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }



                SearchFragment searchFragment = new SearchFragment();

                Bundle bundle = new Bundle();

                bundle.putStringArrayList("search_array", new ArrayList<String>());
                searchFragment.setArguments(bundle);

                FragmentManager fragmentManager_search = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction_search = fragmentManager_search.beginTransaction();


                fragmentTransaction_search.replace(R.id.search_sug, searchFragment);

                fragmentTransaction_search.commit();
                getImages(search_edit.getText().toString(),MainActivity.this);
                handled = true;

            }
            return handled;
        }
    });


    search_edit.addTextChangedListener(new TextWatcher() {
        private Timer timer=new Timer();
        private final long DELAY = 500; // milliseconds

        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {


        }

        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        public void afterTextChanged(final Editable s) {
            if(getCurrentFocus()==search_edit) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {

                                getSearchSuggestions(s.toString(), MainActivity.this);
                            }
                        },
                        DELAY
                );
            }
        }
    });





    }



    public  static void getImages(final String search_term, final Activity activity) {
        if(!search_term.isEmpty()) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://api.cognitive.microsoft.com/bing/v5.0/images/search?q=" + search_term.replaceAll("\\s+", "+") + "&count=150",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            Toast.makeText(activity,response,Toast.LENGTH_SHORT).show();


                            JSONObject jsonResponse = null;
                            ArrayList<ImageHolder> imageHolder = new ArrayList<>();
                            try {
                                jsonResponse = new JSONObject(response);
                                JSONArray value = new JSONArray(jsonResponse.getString("value"));

                                //System.out.println(value);
                                for (int i = 0; i < value.length(); i++) {

                                    ImageHolder temp = new ImageHolder();
                                    temp.setName(value.getJSONObject(i).getString("name"));
                                    temp.setThumb_url(value.getJSONObject(i).getString("thumbnailUrl"));
                                    temp.setThumb_height(value.getJSONObject(i).getJSONObject("thumbnail").getInt("height"));
                                    temp.setThumb_width(value.getJSONObject(i).getJSONObject("thumbnail").getInt("width"));

                                    temp.setWidth(value.getJSONObject(i).getInt("width"));
                                    temp.setHeight(value.getJSONObject(i).getInt("height"));
                                    temp.setContent_url(value.getJSONObject(i).getString("contentUrl"));

                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

                                    try {
                                        Date date = sdf.parse(value.getJSONObject(i).getString("datePublished"));
                                        temp.setDate_published(date);
                                    } catch (Exception e) {
                                        temp.setDate_published(new Date());
                                        continue;
                                    }
                                    imageHolder.add(temp);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            ImagesFragment imagesFragment = new ImagesFragment();
                            if (imageHolder.size() == 0) {
                                Toast.makeText(activity, "No Images Found!Try Again with another search Term", Toast.LENGTH_SHORT).show();
                                System.out.println("NO RESULTS");
                            }

                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("images_arraylist", imageHolder);
                            imagesFragment.setArguments(bundle);

                            FragmentManager fragmentManager_images = ((AppCompatActivity) activity).getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction_images = fragmentManager_images.beginTransaction();


                            fragmentTransaction_images.replace(R.id.fragment_images, imagesFragment);
                            //fragmentTransaction.add(R.id.fragment_images,imagesFragment);
                            fragmentTransaction_images.commit();
                            //System.out.println(response);
                            //System.out.println(imageHolder.size());

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(activity, "Please Check your Internet Connection", Toast.LENGTH_LONG).show();
                        }
                    }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Ocp-Apim-Subscription-Key", "b06765cbec71426b8d9099befc24fa1d");
                    headers.put("User-Agent", "");
                    headers.put("X-Search-ClientIP", "999.999.999.999");
                    headers.put("Host", "api.cognitive.microsoft.com ");
                    //headers.put("X-Search-Location","");
                    return headers;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("mkt", "en-in");
                    params.put("safeSearch", "Strict");


                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(activity);
            requestQueue.add(stringRequest);
        }

    }


    public static void getSearchSuggestions(String search_text, final Activity activity){


            StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://api.cognitive.microsoft.com/bing/v5.0/suggestions?q=" + search_text.replaceAll("\\s+","+"),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            JSONObject jsonResponse = null;
                            ArrayList<String> search_array = new ArrayList<>();
                            try {
                                jsonResponse = new JSONObject(response);
                                JSONArray suggestionGroups = new JSONArray(jsonResponse.getString("suggestionGroups"));
                                JSONArray suggestions = suggestionGroups.getJSONObject(0).getJSONArray("searchSuggestions");

                                //System.out.println(suggestions);

                                for (int i = 0; i < suggestions.length(); i++) {

                                    search_array.add(suggestions.getJSONObject(i).getString("displayText"));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            SearchFragment searchFragment = new SearchFragment();

                            Bundle bundle = new Bundle();

                            bundle.putStringArrayList("search_array", search_array);
                            searchFragment.setArguments(bundle);

                            FragmentManager fragmentManager_search = ((AppCompatActivity) activity).getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction_search = fragmentManager_search.beginTransaction();


                            fragmentTransaction_search.replace(R.id.search_sug, searchFragment);
                            //fragmentTransaction.add(R.id.fragment_images,imagesFragment);
                            fragmentTransaction_search.commit();


                            //System.out.println(search_array);


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                           // Toast.makeText(activity, "Please Check your Internet Connection", Toast.LENGTH_LONG).show();
                        }
                    }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Ocp-Apim-Subscription-Key", "c47e95e475654feeb75458886b06b38c");
                    headers.put("X-Search-ClientIP", "999.999.999.999");
                    headers.put("Host", "api.cognitive.microsoft.com ");

                    return headers;
                }

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("mkt", "en-in");


                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(activity);
            requestQueue.add(stringRequest);

        }


    }

