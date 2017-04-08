package eceapp.ChitChat;

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

public class JoinGroup extends ListActivity {

    static ArrayList<Group> groupNames = new ArrayList<Group>(); // list of group names
//    private ArrayAdapter<Group> adapter;
//    private Button join;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.join_group);
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, groupNames);
//        setListAdapter(adapter);

        LinearLayout.LayoutParams layoutParams = new
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        for (int i = 0; i < groupNames.size(); i++) {
            // Display the groups as buttons so they can be [clicked and] joined.
            Button group = new Button(this);
            group.setText(groupNames.get(i).groupName);
            group.setId(i);
            group.setTextSize(15.0f);
            group.setLayoutParams(layoutParams);

            // Add listener so that when the user selects a group, they can join
            group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CreateGroup.group.addMember(AddUser.user);
//                    adapter.notifyDataSetChanged();
                    Intent intent = new Intent(getBaseContext(), SendMessages.class);
                    startActivity(intent);
                }
            });

            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linLayout);
            linearLayout.addView(group, layoutParams);
        }
    }
}