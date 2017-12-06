package es.marser.backgroundtools.widget.confirmation;

import android.app.DialogFragment;

/**
 * @author sergio
 *         Created by Sergio on 09/03/2017.
 */

@SuppressWarnings("unused")
public class ConfirmDialog extends DialogFragment {
/*
    public static void selectCredential(final Context context, String providers, final OnResultValue<String> result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.con_credentialselector_cancel, null);
        builder.setView(view);
        final Dialog dialog = builder.create();

        ImageButton google = (ImageButton) view.findViewById(R.id.fab_google);
        ImageButton facebook = (ImageButton) view.findViewById(R.id.fab_facebook);
        ImageButton password = (ImageButton) view.findViewById(R.id.fab_password);

        if (!providers.contains(User.GOOGLE_PROVIDER)) {
            google.setVisibility(View.GONE);
        }

        if (!providers.contains(User.FACEBOOK_PROVIDER)) {
            facebook.setVisibility(View.GONE);
        }
        if (!providers.contains(User.EMAIL_PASSWORD_PROVIDER)) {
            password.setVisibility(View.GONE);
        }

        //Google
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.onResult(Activity.RESULT_OK, User.GOOGLE_PROVIDER);
                dialog.dismiss();
            }
        });

        //Facebook
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.onResult(Activity.RESULT_OK, User.FACEBOOK_PROVIDER);
                dialog.dismiss();
            }
        });

        //Password
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.onResult(Activity.RESULT_OK, User.EMAIL_PASSWORD_PROVIDER);
                dialog.dismiss();
            }
        });


        view.findViewById(R.id.cancelar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.onResult(Activity.RESULT_CANCELED, null);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void inputLogin(final Context context,
                                  final String mail, final String pass,
                                  final OnResultLogin result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.con_login_ok_cancel, null);
        builder.setView(view);

        final Dialog dialog = builder.create();
        final TextInputEditText mail_value = (TextInputEditText) view.findViewById(R.id.box_mail);
        final TextInputLayout mail_layout = (TextInputLayout) view.findViewById(R.id.ed_mail);

        mail_value.setText(TextTools.nc(mail));

        view.findViewById(R.id.fab_paste_pass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager)
                        context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);

                if (item.getText() != null) {
                    mail_value.setText(item.getText().toString());
                }
            }
        });
        final TextInputEditText pass_value = (TextInputEditText) view.findViewById(R.id.box_pass);
        final TextInputLayout pass_layout = (TextInputLayout) view.findViewById(R.id.ed_password);

        pass_value.setText(TextTools.nc(pass));

        view.findViewById(R.id.fab_paste_mail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager)
                        context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);

                if (item.getText() != null) {
                    pass_value.setText(item.getText().toString());
                }
            }
        });

        view.findViewById(R.id.aceptar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.requestFocus();
                //Validamos el dato introducido
                String mail_data = mail_value.getText().toString();
                String mail_verify = TextTools.validateMail(mail_data);

                TextTools.toggleTextInputLayoutError(mail_layout, mail_verify);

                String pass_data = pass_value.getText().toString();
                String pass_verify = TextTools.validatePassword(pass_data);

                TextTools.toggleTextInputLayoutError(pass_layout, pass_verify);

                if (pass_verify != null || mail_verify != null) {
                    return;
                }

                result.onResult(Activity.RESULT_OK, mail_data, pass_data);
                dialog.dismiss();
            }
        });


        view.findViewById(R.id.cancelar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.onResult(Activity.RESULT_CANCELED, null, null);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
*/
/*
    /**
     * @param context
     * @param current_pass
     * @param result
     */
  /*
    public static void inputResetPassword(final Context context,
                                          final String current_pass,
                                          final OnResultResetPassword result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.con_reset_password_ok_cancel, null);
        builder.setView(view);

        final Dialog dialog = builder.create();

        final TextInputEditText current_value = (TextInputEditText) view.findViewById(R.id.current_box);
        final TextInputLayout current_layout = (TextInputLayout) view.findViewById(R.id.ed_current_pass);
        current_value.setText(TextTools.nc(current_pass));

        final TextInputEditText new_value = (TextInputEditText) view.findViewById(R.id.new_box);
        final TextInputLayout new_layout = (TextInputLayout) view.findViewById(R.id.ed_new_pass);

        final TextInputEditText confirm_value = (TextInputEditText) view.findViewById(R.id.confirm_box);
        final TextInputLayout confirm_layout = (TextInputLayout) view.findViewById(R.id.ed_confirm_pass);


        view.findViewById(R.id.aceptar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.requestFocus();
                //Validamos el dato introducido
                String current_data = current_value.getText().toString();
                String current_verify = TextTools.validatePassword(current_data);
                TextTools.toggleTextInputLayoutError(current_layout, current_verify);

                String new_data = new_value.getText().toString();
                String new_verify = TextTools.validatePassword(new_data);
                TextTools.toggleTextInputLayoutError(new_layout, new_verify);

                String confirm_data = confirm_value.getText().toString();
                String confirm_verify = TextTools.validateAndConfirmPassword(new_data, confirm_data);
                TextTools.toggleTextInputLayoutError(confirm_layout, confirm_verify);

                if (confirm_verify != null || new_verify != null || current_verify != null) {
                    return;
                }

                result.onResult(Activity.RESULT_OK, current_data, new_data);
                dialog.dismiss();
            }
        });


        view.findViewById(R.id.cancelar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.onResult(Activity.RESULT_CANCELED, null, null);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

/*
    public interface OnResult {
        void onResult(int result);
    }

    public interface OnResultValue<T extends Object> {
        void onResult(int result, T value);
    }

    public interface OnResultLogin {
        void onResult(int result, String mail, String pass);
    }

    public interface OnResultResetPassword {
        void onResult(int result, String old_pass, String new_pass);
    }

    */
}