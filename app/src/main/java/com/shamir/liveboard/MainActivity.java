package com.shamir.liveboard;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText senderEditText;
    private EditText messageEditText;
    private FirebaseFirestore db;
    private final List<Message> messagesList = new ArrayList<>();
    private ArrayAdapter<Message> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button sendButton = findViewById(R.id.sendButton);
        ListView messagesListView = findViewById(R.id.messagesListView);

        // מנגנון להצגת רשימה של מחרוזות נגללת אחת אחרי השניה
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messagesList);
        messagesListView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        sendButton.setOnClickListener(view -> {
            // ודאו שהקלט אינו ריק
            // TODO 2: צרו Message מהשם ומהתוכן.
            // צרו מחלקת MESSAGE עם שני שדות: sender ו-content, והוסיפו לה קונסטרקטור.
            // לא לשכוח TO STRING כדי להציג את ההודעה ברשימה.

            // TODO 3: שמרו את ההודעה החדשה באוסף Firestore בשם "board".
        });

        // האזנה בזמן אמת לשינויים - בכל עדכן או הודעה חדשה - הפעולה תזומן
        db.collection("board").addSnapshotListener((snapshots, error) -> {
            if (error != null || snapshots == null) {
                return;
            }

            messagesList.clear();
            for (QueryDocumentSnapshot document : snapshots) {
                messagesList.add(document.toObject(Message.class));
            }
            adapter.notifyDataSetChanged();
        });
    }
}
