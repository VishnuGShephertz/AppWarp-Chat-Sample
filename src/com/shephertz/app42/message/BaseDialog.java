package com.shephertz.app42.message;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class BaseDialog extends Dialog {
private RelativeLayout dialogView;
protected MessageData options;
private boolean isClosing = false;
protected BaseDialog(Activity activity, boolean fullscreen, MessageData options) {
 super(activity, getTheme(activity));
 //SizeUtil.init(activity);
 this.options = options;
 dialogView = new RelativeLayout(activity);
 LayoutParams layoutParams = new LayoutParams(
     LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
 dialogView.setBackgroundColor(0x00000000);
 dialogView.setLayoutParams(layoutParams);

 RelativeLayout view =null; //createContainerView(activity, fullscreen);
 view.setId(108);
 dialogView.addView(view, view.getLayoutParams());

 Button closeButton = null;//createCloseButton(activity, fullscreen, view);
 dialogView.addView(closeButton, closeButton.getLayoutParams());

 setContentView(dialogView, dialogView.getLayoutParams());

 dialogView.setAnimation(createFadeInAnimation());

 if (!fullscreen) {
   getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
   //getWindow().setDimAmount(0.7f);
 }
}

private Animation createFadeInAnimation() {
 Animation fadeIn = new AlphaAnimation(0, 1);
 fadeIn.setInterpolator(new DecelerateInterpolator());
 fadeIn.setDuration(350);
 return fadeIn;
}

private Animation createFadeOutAnimation() {
 Animation fadeOut = new AlphaAnimation(1, 0);
 fadeOut.setInterpolator(new AccelerateInterpolator());
 fadeOut.setDuration(350);
 return fadeOut;
}

//@Override
//public void cancel() {
// if (isClosing) {
//   return;
// }
// isClosing = true;
// Animation animation = createFadeOutAnimation();
// animation.setAnimationListener(new Animation.AnimationListener() {
//   @Override public void onAnimationStart(Animation animation) {}
//   @Override public void onAnimationRepeat(Animation animation) {}
//   @Override
//   public void onAnimationEnd(Animation animation) {
//     Handler handler = new Handler();
//     handler.postDelayed(new Runnable() {
//         @Override
//         public void run() {
//        	 BaseDialog.super.cancel();
//         }
//     }, 10);
//   }
// });
// dialogView.startAnimation(animation);
//}

//private CloseButton createCloseButton(Activity context, boolean fullscreen, View parent) {
// CloseButton closeButton = new CloseButton(context);
// closeButton.setId(103);
// RelativeLayout.LayoutParams closeLayout = new RelativeLayout.LayoutParams(
//     LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
// if (fullscreen) {
//   closeLayout.addRule(RelativeLayout.ALIGN_PARENT_TOP, dialogView.getId());
//   closeLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, dialogView.getId());
//   closeLayout.setMargins(0, SizeUtil.dp5, SizeUtil.dp5, 0);
// } else {
//   closeLayout.addRule(RelativeLayout.ALIGN_TOP, parent.getId());
//   closeLayout.addRule(RelativeLayout.ALIGN_RIGHT, parent.getId());
//   closeLayout.setMargins(0, -SizeUtil.dp7, -SizeUtil.dp7, 0);
// }
// closeButton.setLayoutParams(closeLayout);
// closeButton.setOnClickListener(new View.OnClickListener() {
//   @Override
//   public void onClick(View arg0) {
//     cancel();
//   }
// });
// return closeButton;
//}

//private RelativeLayout createContainerView(Activity context, boolean fullscreen) {/}

private Shape createRoundRect(int cornerRadius) {
 int c = cornerRadius;
 float[] outerRadii = new float[] { c, c, c, c, c, c, c, c };
 return new RoundRectShape(outerRadii, null, null);
}

//private ImageView createBackgroundImageView(Context context, boolean fullscreen) {
// BackgroundImageView view = new BackgroundImageView(context, fullscreen);
// view.setScaleType(ImageView.ScaleType.CENTER_CROP);
// int cornerRadius;
// if (!fullscreen) {
//   cornerRadius = SizeUtil.dp20;
// } else {
//   cornerRadius = 0;
// }
// view.setImageBitmap(options.getBackgroundImage());
// ShapeDrawable footerBackground = new ShapeDrawable();
// footerBackground.setShape(createRoundRect(cornerRadius));
// footerBackground.getPaint().setColor(options.getBackgroundColor());
// view.setBackgroundDrawable(footerBackground);
// RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
//     LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
// view.setLayoutParams(layoutParams);
// return view;
//}

//private RelativeLayout createTitleView(Context context) {
// RelativeLayout view = new RelativeLayout(context);
// view.setLayoutParams(new LayoutParams(
//     LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//
// TextView title = new TextView(context);
// title.setPadding(0, SizeUtil.dp5, 0, SizeUtil.dp5);
// title.setGravity(Gravity.CENTER);
// title.setText(options.getTitle());
// title.setTextColor(options.getTitleColor());
// title.setTextSize(TypedValue.COMPLEX_UNIT_SP, SizeUtil.textSize0);
// title.setTypeface(null, Typeface.BOLD);
// RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
//     LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
// layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
// layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
// title.setLayoutParams(layoutParams);
//
// view.addView(title, title.getLayoutParams());
// return view;
//}

//private TextView createMessageView(Context context) {
// TextView view = new TextView(context);
// RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
//     LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
// view.setLayoutParams(layoutParams);
// view.setGravity(Gravity.CENTER);
// view.setText(options.getMessageText());
// view.setTextColor(options.getMessageColor());
// view.setTextSize(TypedValue.COMPLEX_UNIT_SP, SizeUtil.textSize0_1);
// return view;
//}
//
//private TextView createAcceptButton(Context context) {
// TextView view = new TextView(context);
// RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
//     LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
// layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
// layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
// layoutParams.setMargins(0, 0, 0, SizeUtil.dp5);
//
// view.setPadding(SizeUtil.dp20, SizeUtil.dp5, SizeUtil.dp20, SizeUtil.dp5);
// view.setLayoutParams(layoutParams);
// view.setText(options.getAcceptButtonText());
// view.setTextColor(options.getAcceptButtonTextColor());
// view.setTypeface(null, Typeface.BOLD);
//
// BitmapUtil.stateBackgroundDarkerByPercentage(view,
//     options.getAcceptButtonBackgroundColor(), 30);
//
// view.setTextSize(TypedValue.COMPLEX_UNIT_SP, SizeUtil.textSize0_1);
// view.setOnClickListener(new View.OnClickListener() {
//   @Override
//   public void onClick(View arg0) {
//     if (!isClosing) {
//       options.accept();
//       cancel();
//     }
//   }
// });
// return view;
//}

private static int getTheme(Activity activity) {
 boolean full = (activity.getWindow().getAttributes().flags &
     WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN;
 if (full) {
   return android.R.style.Theme_Translucent_NoTitleBar_Fullscreen;
 } else {
   return android.R.style.Theme_Translucent_NoTitleBar;
 }
}
}
