package id.web.sasmitorr.menu_cafe_dropbox;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class MainActivity extends AppCompatActivity {

    private static final String urlListMenu = "https://www.dropbox.com/s/pt3pshhbbteu23o/menu_cafe.xml?dl=1";
    private List<Item> listData;
    private GridView gridView;
    private TextView txtTotal;
    private SwipeRefreshLayout swipe;
    private int totalHarga = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        txtTotal = (TextView) findViewById(R.id.total);
        gridView = (GridView) findViewById(R.id.gridViewMenu);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popUpItem(listData.get(position));
            }
        });

        if (listData == null) {
            new MyTask().execute(urlListMenu);
        }

        swipe = (SwipeRefreshLayout) findViewById(R.id.swipeMenu);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                txtTotal.setText("Total : Rp. 0");
                new MyTask().execute(urlListMenu);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.checkout:
                new MyTask().execute(urlListMenu);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class MyTask extends AsyncTask<String, Void, Void> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading Menu cafe");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            listData = getData(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (null != pDialog && pDialog.isShowing()) {
                pDialog.dismiss();
            }

            swipe.setRefreshing(false);
            if (null == listData || listData.size() == 0) {
                Toast.makeText(MainActivity.this, "Menu Tidak Ditemukan", Toast.LENGTH_LONG);
            } else {
                gridView.setAdapter(new CustomAdapterGridview(MainActivity.this, R.layout.gridview_layout, listData));
            }

            setTotal(0);
        }
    }

    public List<Item> getData(String url) {
        Item objItem;
        List<Item> listItem = null;

        try {
            listItem = new ArrayList<>();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new URL(url).openStream());
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("item");

            int batas = nList.getLength();

            for (int temp = 0; temp < batas; temp++) {
                {
                    Node nNode = nList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        objItem = new Item();
                        objItem.setNama(getTagValue("nama", eElement));
                        objItem.setHarga(getTagValue("harga", eElement));
                        objItem.setLink(getTagValue("link", eElement));
                        listItem.add(objItem);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listItem;
    }

    private static String getTagValue(String sTag, Element eElement) {
        NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
                .getChildNodes();
        Node nValue = nlList.item(0);
        return nValue.getNodeValue();
    }

    private void popUpItem(final Item data) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

        LayoutInflater inflater = getLayoutInflater();
        View convertView = inflater.inflate(R.layout.item_layout, null);
        ImageView imgItem = (ImageView) convertView.findViewById(R.id.imgItem);
        TextView txtDesc = (TextView) convertView.findViewById(R.id.txtDesc);
        final EditText txtJml = (EditText) convertView.findViewById(R.id.txtJml);

        Glide.with(this).load(data.getLink()).into(imgItem);
        txtDesc.setText(data.getNama() + " | Rp." + data.getHarga());

        alertDialog.setView(convertView).setTitle("");
        final AlertDialog mAlertDialog = alertDialog.setPositiveButton("PESAN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int pesanan = Integer.valueOf(data.getHarga());
                if (!txtJml.getText().toString().equals("") && !txtJml.getText().toString().equals("0")) {
                    pesanan = Integer.valueOf(txtJml.getText().toString()) * Integer.valueOf(data.getHarga());
                }
                setTotal(pesanan);
            }
        }).create();

        mAlertDialog.show();
    }

    private void setTotal(int nilai) {
        totalHarga = totalHarga + nilai;
        txtTotal.setText("Total : Rp. " + totalHarga);
    }
}
