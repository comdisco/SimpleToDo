package com.example.todoapp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class TodoActivity extends Activity {
	private ArrayList<String> items;
	private ArrayAdapter<String> itemsAdapter;
	private ListView lvItems;
	private EditText etNewItem;
	private final int REQUEST_CODE = 20;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		etNewItem = (EditText) findViewById(R.id.etNewItem);
		lvItems = (ListView) findViewById(R.id.lvItems);
		readItems();
		itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		lvItems.setAdapter(itemsAdapter);
		setupListViewListener();
	}
	
	private void setupListViewListener()
	{
		lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View item,
					int pos, long id) {
				items.remove(pos);
				itemsAdapter.notifyDataSetChanged();
				saveItems();
				return true;
			}
			
		});
		lvItems.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View item, int pos,
					long id) {
				String itemText = items.get(pos).toString();
				// TODO Add Intent here (pass itemText and pos to edit form activity)
				Intent i = new Intent(TodoActivity.this, EditItemActivity.class);
				i.putExtra("itemText", itemText);
				i.putExtra("index", pos);
				startActivityForResult(i, REQUEST_CODE);
				return;
			}
		});
	}
	
	public void addTodoItem(View v)
	{
		String itemText = etNewItem.getText().toString();
		itemsAdapter.add(itemText);
		etNewItem.setText("");
		saveItems();
	}
	
	private void readItems()
	{
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try
		{
			items = new ArrayList<String>(FileUtils.readLines(todoFile));
		}
		catch (IOException e)
		{
			items = new ArrayList<String>();
			e.printStackTrace();
		}
	}
	
	private void saveItems()
	{
		File filesDir = getFilesDir();
		File todoFile = new File(filesDir, "todo.txt");
		try
		{
			FileUtils.writeLines(todoFile, items);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.todo, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE)
		{
			int index = data.getExtras().getInt("index");
			String newText = data.getExtras().getString("newText");
			items.set(index, newText);
			itemsAdapter.notifyDataSetChanged();
			saveItems();
		}
	}

}
