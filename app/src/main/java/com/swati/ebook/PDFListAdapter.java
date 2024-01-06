package com.swati.ebook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

public class PDFListAdapter extends ArrayAdapter<File> {

    private Context Context;
    private List<File> PDFFiles;

    public PDFListAdapter(Context context, List<File> pdfFiles) {
        super(context, R.layout.list_item, pdfFiles);
        Context = context;
        PDFFiles = pdfFiles;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(Context).inflate(R.layout.list_item, parent, false);
        }

        File pdfFile = PDFFiles.get(position);

        TextView tvFileName = convertView.findViewById(R.id.tv_file_name);
        tvFileName.setText(pdfFile.getName());

        TextView tvFileSize = convertView.findViewById(R.id.tv_file_size);
        tvFileSize.setText(getFileSizeString(pdfFile.length()));

        ImageView pdfIcon = convertView.findViewById(R.id.pdf_icon);
        ImageView dltIcon = convertView.findViewById(R.id.delete_icon);

        dltIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFile(position);
            }
        });

        return convertView;
    }

    private String getFileSizeString(long fileSize) {
        if (fileSize <= 0) {
            return "0 B";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(fileSize) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(fileSize / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    private void deleteFile(int position) {
        File file = PDFFiles.get(position);
        if (file.delete()) {
            PDFFiles.remove(position);
            notifyDataSetChanged();
            Toast.makeText(Context, "File deleted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Context, "Unable to delete file", Toast.LENGTH_SHORT).show();
        }
    }

}
