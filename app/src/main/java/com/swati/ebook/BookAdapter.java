package com.swati.ebook;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import com.squareup.picasso.Picasso;

public class BookAdapter extends BaseAdapter {

    private Context context;
    private List<BookModel> books;

    public BookAdapter(Context context, List<BookModel> books) {
        this.context = context;
        this.books = books;
    }

    @Override
    public int getCount(){
        return  books.size();
    }

    @Override
    public Object getItem(int position){
        return  books.get(position);
    }

    @Override
    public long getItemId(int position){
        return  position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_item,parent,false);
        }

        //get the current book from dataset
        BookModel book = books.get(position);

        //bind the data to the views in the layout
        ImageView imageView = convertView.findViewById(R.id.grid_image);
        TextView bookName = convertView.findViewById(R.id.book_title);
        TextView bookCategory = convertView.findViewById(R.id.book_category);
        TextView bookAuthor = convertView.findViewById(R.id.author_name);

        bookName.setText(book.getBookName());
        bookCategory.setText(book.getBookCategory());
        bookAuthor.setText(book.getBookAuthor());

        if(book.getPdfIconUrl()!=null && book.getPdfIconUrl().length()>0) {
            Picasso.get()
                    .load(book.getPdfIconUrl())
                    .resize(300,300)
                    .placeholder(R.drawable.placeholder_image)
                    .into(imageView);
        }else{
            Toast.makeText(context,"Empty image url",Toast.LENGTH_LONG).show();
            Picasso.get().load(R.drawable.report).into(imageView);
        }

        // set an onclick listener on the image view to launch the pdf viewer activity
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, book.getBookName(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, PDFactivity.class);
                intent.putExtra("PATH" , book.getPdfUrl());
                context.startActivity(intent);
            }
        });

        return  convertView;

    }

}
