// Generated by view binder compiler. Do not edit!
package FPT.PRO1122.Nhom3.DuAn1.databinding;

import FPT.PRO1122.Nhom3.DuAn1.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityCreateProfileBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final CardView btnBack;

  @NonNull
  public final AppCompatButton createBtn;

  @NonNull
  public final TextInputEditText edtAddress;

  @NonNull
  public final TextInputEditText edtEmailAddress;

  @NonNull
  public final TextInputEditText edtFirstName;

  @NonNull
  public final TextInputEditText edtLastName;

  @NonNull
  public final LinearLayout llDropdown;

  @NonNull
  public final TextInputLayout tilAddress;

  @NonNull
  public final TextInputLayout tilEmailAddress;

  @NonNull
  public final TextInputLayout tilFirstName;

  @NonNull
  public final TextInputLayout tilLastName;

  @NonNull
  public final TextView tvHeaderText;

  @NonNull
  public final TextView tvWelcomeBack;

  private ActivityCreateProfileBinding(@NonNull LinearLayout rootView, @NonNull CardView btnBack,
      @NonNull AppCompatButton createBtn, @NonNull TextInputEditText edtAddress,
      @NonNull TextInputEditText edtEmailAddress, @NonNull TextInputEditText edtFirstName,
      @NonNull TextInputEditText edtLastName, @NonNull LinearLayout llDropdown,
      @NonNull TextInputLayout tilAddress, @NonNull TextInputLayout tilEmailAddress,
      @NonNull TextInputLayout tilFirstName, @NonNull TextInputLayout tilLastName,
      @NonNull TextView tvHeaderText, @NonNull TextView tvWelcomeBack) {
    this.rootView = rootView;
    this.btnBack = btnBack;
    this.createBtn = createBtn;
    this.edtAddress = edtAddress;
    this.edtEmailAddress = edtEmailAddress;
    this.edtFirstName = edtFirstName;
    this.edtLastName = edtLastName;
    this.llDropdown = llDropdown;
    this.tilAddress = tilAddress;
    this.tilEmailAddress = tilEmailAddress;
    this.tilFirstName = tilFirstName;
    this.tilLastName = tilLastName;
    this.tvHeaderText = tvHeaderText;
    this.tvWelcomeBack = tvWelcomeBack;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityCreateProfileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityCreateProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_create_profile, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityCreateProfileBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnBack;
      CardView btnBack = ViewBindings.findChildViewById(rootView, id);
      if (btnBack == null) {
        break missingId;
      }

      id = R.id.createBtn;
      AppCompatButton createBtn = ViewBindings.findChildViewById(rootView, id);
      if (createBtn == null) {
        break missingId;
      }

      id = R.id.edtAddress;
      TextInputEditText edtAddress = ViewBindings.findChildViewById(rootView, id);
      if (edtAddress == null) {
        break missingId;
      }

      id = R.id.edtEmailAddress;
      TextInputEditText edtEmailAddress = ViewBindings.findChildViewById(rootView, id);
      if (edtEmailAddress == null) {
        break missingId;
      }

      id = R.id.edtFirstName;
      TextInputEditText edtFirstName = ViewBindings.findChildViewById(rootView, id);
      if (edtFirstName == null) {
        break missingId;
      }

      id = R.id.edtLastName;
      TextInputEditText edtLastName = ViewBindings.findChildViewById(rootView, id);
      if (edtLastName == null) {
        break missingId;
      }

      id = R.id.llDropdown;
      LinearLayout llDropdown = ViewBindings.findChildViewById(rootView, id);
      if (llDropdown == null) {
        break missingId;
      }

      id = R.id.tilAddress;
      TextInputLayout tilAddress = ViewBindings.findChildViewById(rootView, id);
      if (tilAddress == null) {
        break missingId;
      }

      id = R.id.tilEmailAddress;
      TextInputLayout tilEmailAddress = ViewBindings.findChildViewById(rootView, id);
      if (tilEmailAddress == null) {
        break missingId;
      }

      id = R.id.tilFirstName;
      TextInputLayout tilFirstName = ViewBindings.findChildViewById(rootView, id);
      if (tilFirstName == null) {
        break missingId;
      }

      id = R.id.tilLastName;
      TextInputLayout tilLastName = ViewBindings.findChildViewById(rootView, id);
      if (tilLastName == null) {
        break missingId;
      }

      id = R.id.tvHeaderText;
      TextView tvHeaderText = ViewBindings.findChildViewById(rootView, id);
      if (tvHeaderText == null) {
        break missingId;
      }

      id = R.id.tvWelcomeBack;
      TextView tvWelcomeBack = ViewBindings.findChildViewById(rootView, id);
      if (tvWelcomeBack == null) {
        break missingId;
      }

      return new ActivityCreateProfileBinding((LinearLayout) rootView, btnBack, createBtn,
          edtAddress, edtEmailAddress, edtFirstName, edtLastName, llDropdown, tilAddress,
          tilEmailAddress, tilFirstName, tilLastName, tvHeaderText, tvWelcomeBack);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
