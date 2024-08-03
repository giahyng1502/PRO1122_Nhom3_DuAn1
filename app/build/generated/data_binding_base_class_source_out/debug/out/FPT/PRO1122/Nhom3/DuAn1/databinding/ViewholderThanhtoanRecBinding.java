// Generated by view binder compiler. Do not edit!
package FPT.PRO1122.Nhom3.DuAn1.databinding;

import FPT.PRO1122.Nhom3.DuAn1.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ViewholderThanhtoanRecBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final TextView numTxtInCheckOut;

  @NonNull
  public final ImageView picCheckout;

  @NonNull
  public final TextView titleTxtCheckout;

  @NonNull
  public final TextView totalPriceTxtInCheckout;

  private ViewholderThanhtoanRecBinding(@NonNull CardView rootView,
      @NonNull TextView numTxtInCheckOut, @NonNull ImageView picCheckout,
      @NonNull TextView titleTxtCheckout, @NonNull TextView totalPriceTxtInCheckout) {
    this.rootView = rootView;
    this.numTxtInCheckOut = numTxtInCheckOut;
    this.picCheckout = picCheckout;
    this.titleTxtCheckout = titleTxtCheckout;
    this.totalPriceTxtInCheckout = totalPriceTxtInCheckout;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static ViewholderThanhtoanRecBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ViewholderThanhtoanRecBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.viewholder_thanhtoan_rec, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ViewholderThanhtoanRecBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.numTxtInCheckOut;
      TextView numTxtInCheckOut = ViewBindings.findChildViewById(rootView, id);
      if (numTxtInCheckOut == null) {
        break missingId;
      }

      id = R.id.picCheckout;
      ImageView picCheckout = ViewBindings.findChildViewById(rootView, id);
      if (picCheckout == null) {
        break missingId;
      }

      id = R.id.titleTxtCheckout;
      TextView titleTxtCheckout = ViewBindings.findChildViewById(rootView, id);
      if (titleTxtCheckout == null) {
        break missingId;
      }

      id = R.id.totalPriceTxtInCheckout;
      TextView totalPriceTxtInCheckout = ViewBindings.findChildViewById(rootView, id);
      if (totalPriceTxtInCheckout == null) {
        break missingId;
      }

      return new ViewholderThanhtoanRecBinding((CardView) rootView, numTxtInCheckOut, picCheckout,
          titleTxtCheckout, totalPriceTxtInCheckout);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
