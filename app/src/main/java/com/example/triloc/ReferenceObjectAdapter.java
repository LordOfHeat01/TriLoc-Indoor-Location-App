package com.example.triloc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReferenceObjectAdapter extends RecyclerView.Adapter<ReferenceObjectAdapter.ViewHolder> {

    private List<ReferenceObject> objectList;

    public ReferenceObjectAdapter(List<ReferenceObject> objectList) {
        this.objectList = objectList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reference_object, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReferenceObject object = objectList.get(position);
        holder.objectName.setText(object.getName());

        String heightAngleText = String.format("Height: %.2f m | Angle: %.2f°",
                object.getObjectHeight(), object.getHorizontalAngle());

        holder.objectDetails.setText(heightAngleText);
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView objectName;
        TextView objectDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            objectName = itemView.findViewById(R.id.objectName);
            objectDetails = itemView.findViewById(R.id.objectCoordinates); // reuse same TextView id
        }
    }
}
