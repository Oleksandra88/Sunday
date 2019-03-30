package pl.teb.android;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuJsonActivity extends AppCompatActivity {
    private static final String TAG = "MenuActivity";
    private static ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_json);
        getMenu(this);
    }

    protected void getMenu(final Context context) {
        final TextView textView = (TextView) findViewById(R.id.aaa);
        final ListView listView = (ListView) findViewById(R.id.menu);
        showSimpleProgressDialog(context, "Loading...", "Fetching Json", false);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.1.194:5544";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.v(TAG, "onResponse");
                        removeSimpleProgressDialog();

                        ArrayList<MenuModel> menuModelArrayList = getInfo(response);
                        MenuAdapter menuAdapter = new MenuAdapter(context, menuModelArrayList);
                        listView.setAdapter(menuAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v(TAG, "onErrorResponse" + error.getMessage());
                        removeSimpleProgressDialog();
                    }
                }


        );

        queue.add(jsonArrayRequest);
    }

    protected ArrayList<MenuModel> getInfo (JSONArray response) {
        ArrayList<MenuModel> menuModelArrayList = new ArrayList<MenuModel> ();

        try {
                       for (int i = 0; i < response.length(); i++) {
                MenuModel playersModel = new MenuModel();
                JSONObject dataobj = response.getJSONObject(i);
                playersModel.setName(dataobj.getString("name"));
                playersModel.setDescription(dataobj.getString("description"));
                playersModel.setPrice(dataobj.getDouble("price"));
                menuModelArrayList.add(playersModel);

                       }
                   } catch (JSONException e) {
                       e.printStackTrace();
        }

        return menuModelArrayList;
    }

    public static void showSimpleProgressDialog(Context context, String title,
                                                String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
