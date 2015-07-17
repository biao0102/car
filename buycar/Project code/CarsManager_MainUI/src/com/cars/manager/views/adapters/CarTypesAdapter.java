package com.cars.manager.views.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.cars.manager.db.table.Pinpais;

public class CarTypesAdapter extends BaseExpandableListAdapter {

	Context mContext;

	ArrayList<String> mGroupList = new ArrayList<String>();

	ArrayList<ArrayList<Pinpais>> mChlidList = new ArrayList<ArrayList<Pinpais>>();

	public CarTypesAdapter(Context context, ArrayList<String> aGroupList, ArrayList<ArrayList<Pinpais>> aChildList) {
		this.mContext = context;
		this.mGroupList = aGroupList;
		this.mChlidList = aChildList;
	}

	@Override
	public int getGroupCount() {
		return mGroupList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return mChlidList.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return mGroupList.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return mChlidList.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		String string = mGroupList.get(groupPosition);
		return getGroupView(string);
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		String string = mChlidList.get(groupPosition).get(childPosition).getName();
		return getChildView(string);
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public TextView getGroupView(String string) {
		// Layout parameters for the ExpandableListView
		AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 64);
		TextView text = new TextView(mContext);
		text.setLayoutParams(layoutParams);
		// Center the text vertically
		text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		// Set the text starting position
		text.setPadding(18, 0, 0, 0);
		text.setText(string);
		text.setTextSize(16);
		return text;
	}

	public TextView getChildView(String string) {
		// Layout parameters for the ExpandableListView
		AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 64);
		TextView text = new TextView(mContext);
		text.setLayoutParams(layoutParams);
		// Center the text vertically
		text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
		// Set the text starting position
		text.setPadding(36, 8, 0, 8);
		text.setTextSize(14);
		text.setText(string);
		return text;
	}

}
