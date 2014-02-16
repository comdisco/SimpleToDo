package com.example.todoapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends Activity {
	private EditText etEditItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_item);
		etEditItem = (EditText) findViewById(R.id.etEditItem);
		String itemText = getIntent().getStringExtra("itemText");
		etEditItem.setText(itemText);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_item, menu);
		return true;
	}
	
	public void onSubmit(View v)
	{
		String newText = etEditItem.getText().toString();
		Intent data = new Intent();
		data.putExtra("newText", newText);
		int index = getIntent().getIntExtra("index", 0);
		data.putExtra("index", index);
		setResult(RESULT_OK, data);
		this.finish();
	}

}
