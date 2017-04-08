package eceapp.ChitChat;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Abby.
 */

public class JoinGroup extends Activity {

//    private ArrayAdapter<Group> adapter;
//    private Button join;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.join_group);
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, groupNames);
//        setListAdapter(adapter);



        // if 1+ groups made
        if (AddUser.user.groupNames.size() > 0) {

            LinearLayout.LayoutParams layoutParams = new
                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            for (int i = 0; i < AddUser.user.groupNames.size(); i++) {
                System.out.println("1: " + AddUser.user.groupNames.get(i).groupName);

                // Display the groups as buttons so they can be [clicked and] joined.
                Button group = new Button(this);

                System.out.println(AddUser.user.groupNames.get(i).groupName.toString());
                group.setText(AddUser.user.groupNames.get(i).groupName.toString());
                group.setId(i);
                group.setTextSize(15.0f);
                group.setLayoutParams(layoutParams);

                final int ind = i;
                // Add listener so that when the user selects a group, they can join
                group.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CreateGroup.group.addMember(AddUser.user);
                        AddUser.user.currGroup = ind; // index of the most recently joined group
//                    adapter.notifyDataSetChanged();
                        Intent intent = new Intent(getBaseContext(), SendMessages.class);
                        startActivity(intent);
                    }
                });

                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linLayout);
                linearLayout.addView(group, layoutParams);

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


    }
}