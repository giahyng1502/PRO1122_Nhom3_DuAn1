// Generated by view binder compiler. Do not edit!
package FPT.PRO1122.Nhom3.DuAn1.databinding;

import FPT.PRO1122.Nhom3.DuAn1.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityChiTietMonAnBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final AppCompatButton AddToCartBtn;

  @NonNull
  public final ImageView backBtn;

  @NonNull
  public final TextView descriptionTxt;

  @NonNull
  public final ImageView favBtn;

  @NonNull
  public final ImageView imageView10;

  @NonNull
  public final ConstraintLayout main;

  @NonNull
  public final TextView minusBtn;

  @NonNull
  public final TextView numTxt;

  @NonNull
  public final ImageView pic;

  @NonNull
  public final TextView plusBtn;

  @NonNull
  public final TextView priceTxt;

  @NonNull
  public final TextView rateTxt;

  @NonNull
  public final RatingBar ratingBar;

  @NonNull
  public final NestedScrollView scrollView2;

  @NonNull
  public final TextView textView124;

  @NonNull
  public final TextView textView16;

  @NonNull
  public final TextView textView18;

  @NonNull
  public final TextView timeTxt;

  @NonNull
  public final TextView titleTxt;

  @NonNull
  public final TextView totalTxt;

  private ActivityChiTietMonAnBinding(@NonNull ConstraintLayout rootView,
      @NonNull AppCompatButton AddToCartBtn, @NonNull ImageView backBtn,
      @NonNull TextView descriptionTxt, @NonNull ImageView favBtn, @NonNull ImageView imageView10,
      @NonNull ConstraintLayout main, @NonNull TextView minusBtn, @NonNull TextView numTxt,
      @NonNull ImageView pic, @NonNull TextView plusBtn, @NonNull TextView priceTxt,
      @NonNull TextView rateTxt, @NonNull RatingBar ratingBar,
      @NonNull NestedScrollView scrollView2, @NonNull TextView textView124,
      @NonNull TextView textView16, @NonNull TextView textView18, @NonNull TextView timeTxt,
      @NonNull TextView titleTxt, @NonNull TextView totalTxt) {
    this.rootView = rootView;
    this.AddToCartBtn = AddToCartBtn;
    this.backBtn = backBtn;
    this.descriptionTxt = descriptionTxt;
    this.favBtn = favBtn;
    this.imageView10 = imageView10;
    this.main = main;
    this.minusBtn = minusBtn;
    this.numTxt = numTxt;
    this.pic = pic;
    this.plusBtn = plusBtn;
    this.priceTxt = priceTxt;
    this.rateTxt = rateTxt;
    this.ratingBar = ratingBar;
    this.scrollView2 = scrollView2;
    this.textView124 = textView124;
    this.textView16 = textView16;
    this.textView18 = textView18;
    this.timeTxt = timeTxt;
    this.titleTxt = titleTxt;
    this.totalTxt = totalTxt;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityChiTietMonAnBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityChiTietMonAnBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_chi_tiet_mon_an, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityChiTietMonAnBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.AddToCartBtn;
      AppCompatButton AddToCartBtn = ViewBindings.findChildViewById(rootView, id);
      if (AddToCartBtn == null) {
        break missingId;
      }

      id = R.id.backBtn;
      ImageView backBtn = ViewBindings.findChildViewById(rootView, id);
      if (backBtn == null) {
        break missingId;
      }

      id = R.id.descriptionTxt;
      TextView descriptionTxt = ViewBindings.findChildViewById(rootView, id);
      if (descriptionTxt == null) {
        break missingId;
      }

      id = R.id.favBtn;
      ImageView favBtn = ViewBindings.findChildViewById(rootView, id);
      if (favBtn == null) {
        break missingId;
      }

      id = R.id.imageView10;
      ImageView imageView10 = ViewBindings.findChildViewById(rootView, id);
      if (imageView10 == null) {
        break missingId;
      }

      ConstraintLayout main = (ConstraintLayout) rootView;

      id = R.id.minusBtn;
      TextView minusBtn = ViewBindings.findChildViewById(rootView, id);
      if (minusBtn == null) {
        break missingId;
      }

      id = R.id.numTxt;
      TextView numTxt = ViewBindings.findChildViewById(rootView, id);
      if (numTxt == null) {
        break missingId;
      }

      id = R.id.pic;
      ImageView pic = ViewBindings.findChildViewById(rootView, id);
      if (pic == null) {
        break missingId;
      }

      id = R.id.plusBtn;
      TextView plusBtn = ViewBindings.findChildViewById(rootView, id);
      if (plusBtn == null) {
        break missingId;
      }

      id = R.id.priceTxt;
      TextView priceTxt = ViewBindings.findChildViewById(rootView, id);
      if (priceTxt == null) {
        break missingId;
      }

      id = R.id.rateTxt;
      TextView rateTxt = ViewBindings.findChildViewById(rootView, id);
      if (rateTxt == null) {
        break missingId;
      }

      id = R.id.ratingBar;
      RatingBar ratingBar = ViewBindings.findChildViewById(rootView, id);
      if (ratingBar == null) {
        break missingId;
      }

      id = R.id.scrollView2;
      NestedScrollView scrollView2 = ViewBindings.findChildViewById(rootView, id);
      if (scrollView2 == null) {
        break missingId;
      }

      id = R.id.textView124;
      TextView textView124 = ViewBindings.findChildViewById(rootView, id);
      if (textView124 == null) {
        break missingId;
      }

      id = R.id.textView16;
      TextView textView16 = ViewBindings.findChildViewById(rootView, id);
      if (textView16 == null) {
        break missingId;
      }

      id = R.id.textView18;
      TextView textView18 = ViewBindings.findChildViewById(rootView, id);
      if (textView18 == null) {
        break missingId;
      }

      id = R.id.timeTxt;
      TextView timeTxt = ViewBindings.findChildViewById(rootView, id);
      if (timeTxt == null) {
        break missingId;
      }

      id = R.id.titleTxt;
      TextView titleTxt = ViewBindings.findChildViewById(rootView, id);
      if (titleTxt == null) {
        break missingId;
      }

      id = R.id.totalTxt;
      TextView totalTxt = ViewBindings.findChildViewById(rootView, id);
      if (totalTxt == null) {
        break missingId;
      }

      return new ActivityChiTietMonAnBinding((ConstraintLayout) rootView, AddToCartBtn, backBtn,
          descriptionTxt, favBtn, imageView10, main, minusBtn, numTxt, pic, plusBtn, priceTxt,
          rateTxt, ratingBar, scrollView2, textView124, textView16, textView18, timeTxt, titleTxt,
          totalTxt);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}