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

public final class ViewholderItemCartBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final TextView minusBtnInCart;

  @NonNull
  public final TextView numTxtInCart;

  @NonNull
  public final ImageView pic;

  @NonNull
  public final TextView plusBtnInCart;

  @NonNull
  public final TextView priceItemTxtInCart;

  @NonNull
  public final TextView titleTxtInCart;

  @NonNull
  public final TextView totalPriceTxtInCart;

  private ViewholderItemCartBinding(@NonNull CardView rootView, @NonNull TextView minusBtnInCart,
      @NonNull TextView numTxtInCart, @NonNull ImageView pic, @NonNull TextView plusBtnInCart,
      @NonNull TextView priceItemTxtInCart, @NonNull TextView titleTxtInCart,
      @NonNull TextView totalPriceTxtInCart) {
    this.rootView = rootView;
    this.minusBtnInCart = minusBtnInCart;
    this.numTxtInCart = numTxtInCart;
    this.pic = pic;
    this.plusBtnInCart = plusBtnInCart;
    this.priceItemTxtInCart = priceItemTxtInCart;
    this.titleTxtInCart = titleTxtInCart;
    this.totalPriceTxtInCart = totalPriceTxtInCart;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static ViewholderItemCartBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ViewholderItemCartBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.viewholder_item_cart, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ViewholderItemCartBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.minusBtnInCart;
      TextView minusBtnInCart = ViewBindings.findChildViewById(rootView, id);
      if (minusBtnInCart == null) {
        break missingId;
      }

      id = R.id.numTxtInCart;
      TextView numTxtInCart = ViewBindings.findChildViewById(rootView, id);
      if (numTxtInCart == null) {
        break missingId;
      }

      id = R.id.pic;
      ImageView pic = ViewBindings.findChildViewById(rootView, id);
      if (pic == null) {
        break missingId;
      }

      id = R.id.plusBtnInCart;
      TextView plusBtnInCart = ViewBindings.findChildViewById(rootView, id);
      if (plusBtnInCart == null) {
        break missingId;
      }

      id = R.id.priceItemTxtInCart;
      TextView priceItemTxtInCart = ViewBindings.findChildViewById(rootView, id);
      if (priceItemTxtInCart == null) {
        break missingId;
      }

      id = R.id.titleTxtInCart;
      TextView titleTxtInCart = ViewBindings.findChildViewById(rootView, id);
      if (titleTxtInCart == null) {
        break missingId;
      }

      id = R.id.totalPriceTxtInCart;
      TextView totalPriceTxtInCart = ViewBindings.findChildViewById(rootView, id);
      if (totalPriceTxtInCart == null) {
        break missingId;
      }

      return new ViewholderItemCartBinding((CardView) rootView, minusBtnInCart, numTxtInCart, pic,
          plusBtnInCart, priceItemTxtInCart, titleTxtInCart, totalPriceTxtInCart);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
