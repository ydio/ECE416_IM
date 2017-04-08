package eceapp.ChitChat;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by Abby.
 */

public class QuitGroup extends Activity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.quit_group);

        LinearLayout.LayoutParams layoutParams = new
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        for (int i = 0; i < AddUser.user.groupNames.size(); i++) {
            // Display the groups as buttons so they can be [clicked and] joined.
            Button group = new Button(this);
            group.setText(AddUser.user.groupNames.get(i).groupName);
            group.setId(i);
            group.setTextSize(15.0f);
            group.setLayoutParams(layoutParams);

            // Add listener so that when the user selects a group, they can join
            group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CreateGroup.group.removeMember(AddUser.user);
                    // print message to say removed
//                    Intent intent = new Intent(getBaseContext(), SendMessages.class);
//                    startActivity(intent);
                }
            });

            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linLayout);
            linearLayout.addView(group, layoutParams);
        }
    }
}
