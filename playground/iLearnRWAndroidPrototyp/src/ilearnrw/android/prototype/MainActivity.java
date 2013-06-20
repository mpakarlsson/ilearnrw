package ilearnrw.android.prototype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ilearnrw.user.LanguageCode;
import ilearnrw.user.ProblemDefinition;
import ilearnrw.user.ProblemDefinitionIndexApi;
import ilearnrw.user.Subcategory;
import ilearnrw.user.mockup.ProblemDefinitionIndexMockup;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		ListView listView = (ListView) findViewById(R.id.listView1);

		ProblemDefinitionIndexApi index = new ProblemDefinitionIndexMockup();

		ArrayList<ProblemDefinition> greekProblems = index
				.getProblemsByLanguage(LanguageCode.GR);
		for (ProblemDefinition p : greekProblems) {

		}

		ProblemDefinitionArrayAdapter adapter = new ProblemDefinitionArrayAdapter(
				this, android.R.layout.simple_list_item_1, greekProblems);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				ProblemDefinition p = (ProblemDefinition) parent.getItemAtPosition(position);
				Subcategory s = p.getType();
				Toast.makeText(getApplicationContext(), String.format("Subcategory: %s", s.getSubcategoryTitle()), Toast.LENGTH_LONG).show();
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class ProblemDefinitionArrayAdapter extends
			ArrayAdapter<ProblemDefinition> {

		HashMap<ProblemDefinition, Integer> mIdMap = new HashMap<ProblemDefinition, Integer>();
		private Context context;

		public ProblemDefinitionArrayAdapter(Context context,
				int textViewResourceId, List<ProblemDefinition> objects) {
			super(context, textViewResourceId, objects);
			this.context = context;
			for (int i = 0; i < objects.size(); ++i) {
				mIdMap.put(objects.get(i), i);
			}
		}

		@Override
		public long getItemId(int position) {
			ProblemDefinition item = getItem(position);
			return mIdMap.get(item);
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			if (view == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.problemdefinitionitem, null);
			}

			ProblemDefinition item = getItem(position);
			if (item != null) {
				// My layout has only one TextView
				TextView itemView = (TextView) view.findViewById(R.id.label);
				if (itemView != null) {
					// do whatever you want with your string and long
					itemView.setText(String.format("%s", item.getURI()));
				}
			}

			return view;
		}

	}

}
