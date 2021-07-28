package com.cw.exitriga.Utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.cw.exitriga.R;


public class CustomAlertdialog {


	public static void createDialog(final Context context, final String message)
	{
		final Dialog dialog = new Dialog(context);


		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
		dialog.setContentView(R.layout.dialog_warning2);
		dialog.setCancelable(true);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialog.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

		TextView text = (TextView) dialog.findViewById(R.id.content);
		text.setText(message);

		((AppCompatButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

					dialog.dismiss();

			}
		});

		dialog.show();
		dialog.getWindow().setAttributes(lp);
	}

}
