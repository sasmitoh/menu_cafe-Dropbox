# menu_cafe-Dropbox
Menu Cafe -> **Dropbox**
>aplikasi ini akan menampilkan menu-menu cafe dimana menu2 tersebut secara realtime bisa kita ubah/edit/hapus sesuai kebutuhan tempat menunya. Teknologi yang di gunakan untuk media penyimpanan menu tersebut adalah dengan menggunakan Dropbox.

## Langkah-langkah build menu_cafe-Dropbox
<ol>
  <li>Memiliki Akun <b>Dropbox</b> Aktiv </li>
   <li>pilih project <b>Empity Activity</b></li>
   <li>Create File.java main\java <b>MainActivity.java Item.Java CustomAdapterGridview.java </b></li>
   <li>Create File.xml main\res\layout <b>main_activity.xml item_layout.xml gridview_layout.xml </b></li>
   <li>Create <b>AndroidManifest.xml</b></li>
</ol>
<hr/>

## Note :
Tambahkan dependency berikut di **build.gradle**. menggunakan library **glide** untuk meng-handle ketika load gambar dari url terus menampilkannya di imageview<br> 
```javascript
dependencies{
  compile fileTree(dir: 'libs', include: ['*.jar'])
  compile 'com.android.support:appcompat-v7:25.3.1'
  compile 'com.android.support:support-v4:25.3.1'
  compile 'com.android.support:design:25.3.1'
  compile 'com.android.support.constraint:constraint-layout:1.0.2'
  compile 'com.github.bumptech.glide:glide:3.7.0' 
}
```   
membuat permission Internet pada AndroidManifest.xml<br>
```<uses-permission android:name="android.permission.INTERNET" />```

membuat folder menu dan create file menu_cafe.xml untuk pemanggilan data yang ada di di folder menu lalu sisipkan image<br>
```https://www.dropbox.com/s/pt3pshhbbteu23o/menu_cafe.xml?dl=1```<br>
rubah angka 0 menjadi 1
<hr/>

## Demo Aplikasi
<img src="https://github.com/sasmitoh/menu_cafe-Dropbox/blob/master/menu1.jpg" width="250" height="350" />
<img src="https://github.com/sasmitoh/menu_cafe-Dropbox/blob/master/menu2.jpg" width="250" height="350" />

[Sasmitoh RR](http://sasmitohrr.web.id)
:octocat:
 
