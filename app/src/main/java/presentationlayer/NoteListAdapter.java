package presentationlayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kushal.rihabhbhandari.R;

import java.util.List;

import persistancelayer.NoteInterface;

/**
 * Created by Matthias on 16-03-12.
 */
public class NoteListAdapter extends BaseAdapter
{
	private Context                 context;
	private List<NoteInterface>     noteList;
	private static LayoutInflater   inflater = null;


	public NoteListAdapter(Context context, List<NoteInterface> noteList)
	{
		this.context = context;
		this.noteList = noteList;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount()
	{ return noteList.size(); }

	@Override
	public Object getItem(int position)
	{ return noteList.get(position); }

	@Override
	public long getItemId(int position)
	{ return position; }

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View view = convertView;
		if (view == null)
			view = inflater.inflate(R.layout.main_list_item, null, false);

		final NoteInterface note = (NoteInterface) getItem(position);

		ImageView iv_thumbnail  = (ImageView) view.findViewById(R.id.main_list_item_iv_thumbnail);
		ImageView iv_pin        = (ImageView) view.findViewById(R.id.main_list_item_iv_pin);
		TextView  tv_title      = (TextView)  view.findViewById(R.id.main_list_item_tv_title);
		TextView  tv_content    = (TextView)  view.findViewById(R.id.main_list_item_tv_content_line);
		TextView  tv_editTime   = (TextView)  view.findViewById(R.id.main_list_item_tv_edit_time);
		TextView  tv_tags       = (TextView)  view.findViewById(R.id.main_list_item_tv_tag);
		TextView  tv_tags_const = (TextView)  view.findViewById(R.id.main_list_item_tv_tag_const);

		tv_title.setText(note.hasNoteTitle() ? note.getNoteTitle() : "Empty Title");
		tv_editTime.setText(note.hasLastEditedTime() ? note.getLastEditedTime() : "Unknown Edit Time");

		if (!note.hasContents())
		{
			tv_content.setVisibility(View.GONE);
		}
		else
		{
			tv_content.setVisibility(View.VISIBLE);
			tv_content.setText(note.getContents());
		}

		if (note.hasImages())
			iv_thumbnail.setImageBitmap(note.getImages().get(0));

		if (!note.hasTag())
		{
			tv_tags.setVisibility(View.GONE);
			tv_tags_const.setVisibility(View.GONE);
		}
		else
		{
			tv_tags.setVisibility(View.VISIBLE);
			tv_tags_const.setVisibility(View.VISIBLE);
			tv_tags.setText(note.getTag());
		}

		iv_pin.setVisibility(note.isPinned() ? View.VISIBLE : View.INVISIBLE);

		return view;
	}
}