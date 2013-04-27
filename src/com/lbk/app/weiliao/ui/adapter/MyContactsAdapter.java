package com.lbk.app.weiliao.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import com.lbk.app.weiliao.R;
import com.lbk.app.weiliao.bean.Friend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

/**
 * @ClassName: MyContactsAdapter
 * @Description: Õ®—∂¬º  ≈‰∆˜
 * @author ¡Œ∞€¿§
 * @date 2013-4-24 œ¬ŒÁ3:54:38
 *
 */
public class MyContactsAdapter extends BaseAdapter implements StickyListHeadersAdapter, SectionIndexer {
	
//	private String[] countries;
	private List<Friend> friends;
	private ArrayList<String> sections;
	private LayoutInflater inflater;
	
	public MyContactsAdapter(Context context, List<Friend> friends) {
		inflater = LayoutInflater.from(context);
//		countries = context.getResources().getStringArray(R.array.countries);
		this.friends = friends;
		sections = new ArrayList<String>();
//		for(String s : countries){
//			if(!sections.contains(""+s.charAt(0))){
//				sections.add(""+s.charAt(0));
//			}
//		}
		for (Friend friend : friends) {
			if(!sections.contains(friend.getName().charAt(0)+"")){
				sections.add(friend.getName().charAt(0)+"");
			}
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
//		return countries.length;
		return friends.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
//		return countries[position];
		return friends.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPositionForSection(int section) {
		if(section >= sections.size()){
			section = sections.size()-1;
		}else if(section < 0){
			section = 0;
		}

		int position = 0;
		char sectionChar = sections.get(section).charAt(0);
//		for(int i = 0 ; i<countries.length ; i++){
//			if(sectionChar == countries[i].charAt(0)){
//				position = i;
//				break;
//			}
//		}
		for (int i = 0; i < friends.size(); i++) {
			if(sectionChar == friends.get(i).getName().charAt(0)){
				position = i;
				break;
			}
		}
		return position;
	}

	@Override
	public int getSectionForPosition(int position) {
//		if(position >= countries.length){
//			position = countries.length-1;
//		}else if(position < 0){
//			position = 0;
//		}
//		return sections.indexOf(""+countries[position].charAt(0));
		
		if(position >= friends.size()){
			position = friends.size()-1;
		}else if(position < 0){
			position = 0;
		}
		return sections.indexOf(friends.get(position).getName().charAt(0)+"");
	}

	@Override
	public Object[] getSections() {
		// TODO Auto-generated method stub
		return sections.toArray(new String[sections.size()]);
	}
	
	public void clearAll() {
//		countries = new String[0];
		friends = new ArrayList<Friend>();
	    sections.clear();
	}
	
	class HeaderViewHolder {
		TextView text;
	}

	class ViewHolder {
		TextView text;
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		HeaderViewHolder holder;
		if (convertView == null) {
			holder = new HeaderViewHolder();
			convertView = inflater.inflate(R.layout.list_contacts_header, parent, false);
			holder.text = (TextView) convertView.findViewById(R.id.text);
			convertView.setTag(holder);
		} else {
			holder = (HeaderViewHolder) convertView.getTag();
		}
		//set header text as first char in name
//		String headerText = "" + countries[position].charAt(0);
		String headerText = friends.get(position).getName().charAt(0)+"";
		holder.text.setText(headerText);
		return convertView;
	}

	@Override
	public long getHeaderId(int position) {
		// TODO Auto-generated method stub
//		return countries[position].charAt(0);
		return friends.get(position).getName().charAt(0);
	}

}
