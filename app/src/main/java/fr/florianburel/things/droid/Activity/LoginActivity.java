package fr.florianburel.things.droid.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import de.greenrobot.inject.Injector;
import de.greenrobot.inject.annotation.InjectView;
import de.greenrobot.inject.annotation.OnClick;
import fr.florianburel.things.droid.R;


public class LoginActivity extends Activity{


    @InjectView(id = R.id.loginTextField)
    private EditText loginTextField;

    @InjectView(id = R.id.passwordTextField)
    private EditText passwordTextField;

    @InjectView(id = R.id.loginButton)
    private Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        Injector.injectInto(this);

        loginTextField.setText("Florian");

    }

    @OnClick(id = R.id.loginButton)
    void OnLoginPressed()
    {
        boolean test = CheckLogin(loginTextField.getText().toString(), passwordTextField.getText().toString());

        if(test)
        {
            Intent i = new Intent(this, MainActivity.class);



            startActivity(i);
        }
        else
        {
            Toast.makeText(getApplicationContext(), "bOOuh :'(", Toast.LENGTH_LONG).show();
        }
    }

    boolean CheckLogin(String login, String password)
    {
        return "Florian".equals(login) && "toto".equals(password);
    }



}
