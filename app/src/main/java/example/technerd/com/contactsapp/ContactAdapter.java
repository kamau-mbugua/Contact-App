package example.technerd.com.contactsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {
    private Context context;
    private List<Contact>contacts;

    public ContactAdapter(@NonNull Context context,List<Contact> list) {
        super(context,R.layout.row_layout);
        this.context = context;
        this.contacts = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView= inflater.inflate(R.layout.row_layout,parent, false);

        TextView tvChar=convertView.findViewById(R.id.tvChar);
        TextView tvName=convertView.findViewById(R.id.tvName);
        TextView tvEmail=convertView.findViewById(R.id.tvEmail);
        TextView tvPhone=convertView.findViewById(R.id.tvPhone);
        TextView tvDop=convertView.findViewById(R.id.tvDob);

      tvChar.setText(contacts.get(position).getName().toUpperCase().charAt(0)+"");
      tvName.setText(contacts.get(position).getName());
      tvEmail.setText(contacts.get(position).getEmail());
      tvDop.setText(contacts.get(position).getDob());
      tvPhone.setText(contacts.get(position).getNumber());
        return convertView;
    }
}
