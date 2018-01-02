package co.rodojo.ubetchya;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ResultCodes;

import java.util.Arrays;
import java.util.List;

public class FirebaseAuthActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_auth);
        TextView offlineMessage = (TextView) findViewById(R.id.firebase_offline_message);
        offlineMessage.setVisibility(View.GONE);

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                new AuthUI.IdpConfig.Builder(AuthUI.PHONE_VERIFICATION_PROVIDER).build());

        // Create and launch sign-in intent

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.FirebaseUIAppTheme)
                        .setLogo(R.drawable.ghost)
                        .build(),
                RC_SIGN_IN);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == ResultCodes.OK) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                TextView offlineMessage = (TextView) findViewById(R.id.firebase_offline_message);
                // Sign in failed, check response for error code
                offlineMessage.setVisibility(View.VISIBLE);
            }
        }
        else {
            TextView offlineMessage = (TextView) findViewById(R.id.firebase_offline_message);
            offlineMessage.setVisibility(View.VISIBLE);
        }
    }
}
