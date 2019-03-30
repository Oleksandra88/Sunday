package pl.teb.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

    public class MenuAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<MenuModel> menuModelArrayList;

        public MenuAdapter(Context context, ArrayList<MenuModel> menuModelArrayList) {

            this.context = context;
            this.menuModelArrayList = menuModelArrayList;
        }

        @Override
        public int getViewTypeCount() {
            return getCount();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public int getCount() {
            return menuModelArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return menuModelArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                holder = new ViewHolder();
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.menu_positions, null, true);

                holder.mnname = (TextView) convertView.findViewById(R.id.name);
                holder.mndescription = (TextView) convertView.findViewById(R.id.description);
                holder.mnprice = (TextView) convertView.findViewById(R.id.price);

                convertView.setTag(holder);
            } else {
                // the getTag returns the viewHolder object set as a tag to the view
                holder = (ViewHolder) convertView.getTag();
            }

            holder.mnname.setText("Name: " + menuModelArrayList.get(position).getName());
            holder.mndescription.setText("Description: " + menuModelArrayList.get(position).getDescription());
            holder.mnprice.setText("Price: " + menuModelArrayList.get(position).getPrice());

            return convertView;
        }

        private class ViewHolder {
            protected TextView mnname, mndescription, mnprice;
        }

    }

