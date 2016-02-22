package com.example.kushal.Handy;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.view.View;

import android.widget.*;
import com.example.kushal.rihabhbhandari.R;

// todo: perhaps rotation for each photo

/**
 * UI Layer: PhotoNoteUI
 * BL: ClickableImageBL, PhotoNoteBL
 * PL: Android Library Functions
 */

public class PhotoNoteUI extends Activity
{
//    private boolean zoomOut = false;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    private LinearLayout root;


    // Database variables
    private ArrayList<ImageView> imageViews;
    private ArrayList<EditText> editTexts;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo_ui);

        // root
        root = (LinearLayout) findViewById(R.id.LinearLayout_Items);

        // Database variables
        imageViews = new ArrayList<>();
        editTexts = new ArrayList<>();

    }

    // ================================================================
    // Methods for Click Listeners
    // ================================================================

    public void onClickAcceptImageButton(View view)
    {
        Toast.makeText(PhotoNoteUI.this, "Photo Note is saved (not yet implemented).", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    public void onClickCancelImageButton(View view)
    {
        Toast.makeText(PhotoNoteUI.this, "Photo Note is deleted.", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

	final int GET_PHOTO = 0;

    public void onClickAddPhotoImageButton(View view)
    {
	    Intent intent = new Intent(this, PhotoNoteBL.class);
//	    intent.putExtra()
	    startActivityForResult(intent, GET_PHOTO);
    }

    // ================================================================
    // Methods for Dialog Results
    // ================================================================

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == GET_PHOTO)
            {
	            Bundle bundle = data.getExtras();
	            Bitmap bm = bundle.getParcelable(PhotoNoteBL.KEY_BITMAP);
	            onSuccessfulAddPhoto(bm);
            }
        }
    }

    private void onSuccessfulAddPhoto(Bitmap bitmap)
    {
        final ImageView ivImage = new PhotoNoteBL().makeImageView(this, getApplicationContext(), bitmap);
        ivImage.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
				showDialogOnLongClickImageButton(ivImage);
                return true;
            }
        });
        addNewImageView(ivImage);
    }

    private void addNewImageView(ImageView imageView)
    {
        // add new ImageView
        imageViews.add(imageView);
        root.addView(imageView);

        // remove last EditText if it's not used
        if (!editTexts.isEmpty())
        {
            EditText lastOne = editTexts.get(editTexts.size() - 1);

            if (lastOne.getText().length() == 0)
            {
                root.removeView(lastOne);
            }
        }

        // add EditText
        EditText newEditText = new EditText(this);
        newEditText.setHint("Continue Your Notes Here");
        editTexts.add(newEditText);
        root.addView(newEditText);
    }

    private void showDialogOnLongClickImageButton(final ImageView imageView)
    {
        final CharSequence[] items = {"Delete from my note", "Rotate", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(PhotoNoteUI.this);
        builder.setTitle("Options for this image");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item)
            {
                if (items[item].equals("Delete from my note"))
                {
                    Toast.makeText(PhotoNoteUI.this, "Deleted", Toast.LENGTH_SHORT).show();
                    removeImageView(imageView);
                }
                else if (items[item].equals("Rotate"))
                {
                    Toast.makeText(PhotoNoteUI.this, "Not yet implemented", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                else if (items[item].equals("Cancel"))
                {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void removeImageView(ImageView imageView)
    {
        root.removeView(imageView);
    }
}

