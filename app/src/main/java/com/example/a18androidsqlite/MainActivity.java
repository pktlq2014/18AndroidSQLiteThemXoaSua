package com.example.a18androidsqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    Database database;
    ListView listView1;
    CongViecAdapter congViecAdapter;
    ArrayList<CongViec> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView1 = findViewById(R.id.listView1);
        arrayList = new ArrayList<>();
        congViecAdapter = new CongViecAdapter(MainActivity.this, R.layout.dong_cong_viec, arrayList);
        listView1.setAdapter(congViecAdapter);
        // Bước 2 sau khi tạo class Database
        database = new Database(MainActivity.this, "ghichu.sqlite", null , 1);
        // Bước 3 tạo table CongViec
        database.QueryData("CREATE TABLE IF NOT EXISTS CongViec(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenCV VARCHAR(200))");
        // Bước 4 insert cột data -> dời xuống dưới rồi
      //  database.QueryData("INSERT INTO CongViec VALUES(null, 'Viết ứng dụng ghi chú')");

        luaChon();
    }
    private void luaChon()
    {
        // Bước 5 select cột data
        Cursor cursor = database.GetData("SELECT * FROM CongViec");
        arrayList.clear(); // -> dòng này để refresh lại sau mỗi lần thêm
        while (cursor.moveToNext())
        {
            // vị trí thứ 2 tính từ id: 0,1
            String ten = cursor.getString(1);
            int id = cursor.getInt(0);
            arrayList.add(new CongViec(id, ten));
        }
        congViecAdapter.notifyDataSetChanged();
    }
    // hiện dấu + thêm vào
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.add_congviec, menu);
        return super.onCreateOptionsMenu(menu);
    }
    // chức năng của nút +
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if(item.getItemId() == R.id.menuAdd)
        {
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }
    // click hiện ra dong_cong_viec
    private void DialogThem()
    {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_cong_viec);


        final EditText editText1 = dialog.findViewById(R.id.editText1);
        Button button1 = dialog.findViewById(R.id.butTon1);
        Button button2 = dialog.findViewById(R.id.butTon2);
        button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String tenCV = editText1.getText().toString();
                if(tenCV.equals("") == true)
                {
                    Toast.makeText(MainActivity.this, "Bạn Chưa Nhập Tên Công Việc", Toast.LENGTH_SHORT).show();
                }
                else
                {
             //       database.QueryData("INSERT INTO CongViec VALUES(null, 'Viết ứng dụng ghi chú')");
                    database.QueryData("INSERT INTO CongViec VALUES(null, '"+ tenCV +"')");
                    Toast.makeText(MainActivity.this, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();



                    luaChon();
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
        dialog.show();
    }



    // khai báo adapter gọi qua
    public void dialogSuaCongViec(final String ten, final int id)
    {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sua);



        final EditText editText2 = dialog.findViewById(R.id.editText2);
        Button butTon3 = dialog.findViewById(R.id.butTon3);
        Button butTon4 = dialog.findViewById(R.id.butTon4);

        // sửa cái nào thì setText lại cái đó trừ ID
        editText2.setText(ten);
        butTon3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String a = editText2.getText().toString().trim();
                database.QueryData("UPDATE CongViec SET TenCV = '"+ a +"' WHERE Id = '"+ id +"'");
                Toast.makeText(MainActivity.this, "Đã Cập Nhật", Toast.LENGTH_SHORT).show();
                dialog.dismiss(); // tắt hộp thoại đi
                luaChon();
            }
        });
        butTon4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    // xóa công việc
    public void dialogXoaCongViec(String tenCongViec, final int id)
    {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(MainActivity.this);
        dialogXoa.setMessage("Bạn Chắc Chắn Muốn Xóa Công Việc: " + tenCongViec + " không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                database.QueryData("DELETE FROM CongViec WHERE Id = '"+ id +"'");
                Toast.makeText(MainActivity.this, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                luaChon();
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        dialogXoa.show();
    }
}
