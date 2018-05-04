package mobwebtech2.com.bcu.kristian.discogsapp.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mobwebtech2.com.bcu.kristian.discogsapp.Models.Member;
import mobwebtech2.com.bcu.kristian.discogsapp.R;

public class MemberAdapter extends ArrayAdapter<Member> {
    public MemberAdapter(Context context, List<Member> members) {
        super(context, 0, members);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Member member = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.member_layout,
                    parent, false);
        }

        TextView artist = convertView.findViewById(R.id.memberName);

        artist.setText(member.getName());

        if (member.isActive()) {

            artist.setTextColor(ContextCompat.getColor(getContext(), R.color.greenColor));
        } else {

            artist.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        }

        return convertView;
    }
}

