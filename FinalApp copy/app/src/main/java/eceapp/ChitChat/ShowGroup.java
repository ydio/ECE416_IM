package eceapp.ChitChat;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Abby.
 */

public class ShowGroup extends ListActivity {

        @Override
        public void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            setContentView(R.layout.show_group);

            LinearLayout.LayoutParams layoutParams = new
                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            for (int i = 0; i < CreateGroup.group.members.size(); i++) {
                TextView group = new Button(this);
                group.setText(CreateGroup.group.members.get(i).name);
                group.setId(i);
                group.setTextSize(15.0f);
                group.setLayoutParams(layoutParams);

                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linLayout);
                linearLayout.addView(group, layoutParams);
            }

            Button back = (Button) findViewById(R.id.home);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), SplashScreen.class);
                    startActivity(intent);
                }
            });
        }
}
