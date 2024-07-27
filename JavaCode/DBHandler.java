package com.example.shopfinder;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.Date;

public class DBHandler extends SQLiteOpenHelper {

    public static final String dbName = "ShopFinder.db";

    public DBHandler(Context context) {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryUsers = "create table Users(UserId integer primary key Autoincrement,UserName varchar(40) not null," +
                " UserCellNo varchar(20) , UserEmail varchar(45) not null unique,\n" +
                "UserPassword Varchar(15) not null)";


        String queryShopKeepers = "create table ShopKeepers(ShopKeeperId integer primary key Autoincrement,ShopKeeperName text not null"
                + ", ShopKeeperCellNo text, ShopKeeperEmail text unique, ShopKeeperPassword text not null)";

        String queryVisitingPlaces = "create table VisitingPlaces(PlaceId integer primary key Autoincrement,PlaceName mediumtext  unique not null," +
                "location mediumtext not null unique ,City varchar(20),AboutPlace mediumtext default'',PlaceType Varchar(15) not null)";

        String queryIncludes="create table Includes(UserId integer ,ShopsId integer , primary key(UserId,ShopsId),constraint usersincludes_fk foreign key(UserID) references Users(UserId),\n" +
                "constraint Shopsincludes_fk foreign key(ShopsID) references Shops(ShopsId))";

        String queryViewing="create table Viewing(ShopsId integer ,UserId integer ,comments varchar(100) default'', ViewDate Date not null default(CURRENT_DATE)," +
                "primary key (ShopsId,UserId),constraint Userview_fk foreign key(UserId) references Users(UserId)\n" +
                ",constraint Shopsview_fk foreign key(ShopsId) references Shops(ShopsId))";


        String queryShops="create table Shops(ShopsId integer primary key Autoincrement,Shopsname varchar(40) not null, ShopsNum Varchar(5),ShopsStreet varchar(15)" +
                ", ShopsCity varchar(20),ShopsArea mediumtext,ShopsType Varchar(15) not null, ShopsRating decimal default 0, ShopkeeperId int,PlaceId int,\n" +
                "constraint Shopkepers_shops_fk foreign key(ShopkeeperId) references ShopKeepers(ShopkeeperId),\n" +
                "constraint VisitingPlaces_shops_fk foreign key(PlaceId) references VisitingPlaces(PlaceId))";

        db.execSQL(queryUsers);
        db.execSQL(queryShopKeepers);
        db.execSQL(queryVisitingPlaces);
        db.execSQL(queryShops);
        db.execSQL(queryIncludes);
        db.execSQL(queryViewing);
    }
    public  Cursor selectNull(){
        return this.getReadableDatabase().rawQuery("select null",null,null);
    }
    public Cursor userSearchShopByVsPlace(String search){
        SQLiteDatabase db = this.getReadableDatabase();
        String qry="select * from shops where PlaceId in (select placeId from visitingplaces where PlaceName like '%"+search+"' or PlaceName like '%"+search+"%' or PlaceName "
                + "like '"+search+"%' or city like '%"+search+"'  or city like '%"+search+"%' or city like '"+search+"%' or PlaceType like '%"+search+"' or PlaceType like "
                + "'%"+search+"%' or PlaceType like '"+search+"%' or location like '%"+search+"' or location like '%"+search+"%' or location like '"+search+"%')";
        Cursor crs=db.rawQuery(qry,null,null);
        crs.moveToFirst();
        while(crs.moveToNext()) {
            Log.e(TAG, crs.getString(0)+" hello "+crs.getString(1)+"  12"+crs.getString(2));
        }
        crs.moveToFirst();
        return crs;
    }
    public Cursor userSearchShopByShop(String search){
        SQLiteDatabase db = this.getReadableDatabase();
        String qry="select * from shops where shopsname like '%"+search+"' or shopsname like '%"+search+"%' or shopsname like '"+search+"%' or ShopsCity like '%"+search+"' \n" +
                "or ShopsCity like '%"+search+"%' or ShopsCity like '"+search+"%' or ShopsArea like '%"+search+"' or ShopsArea like '%"+search+"%' or ShopsArea like '"+search+"%'\n" +
                " or ShopsType like '%"+search+"' or ShopsType like '%"+search+"%' or ShopsType like '"+search+"%' ; ";
        Cursor crs=db.rawQuery(qry,null,null);
        crs.moveToFirst();
        while(crs.moveToNext()) {
            Log.e(TAG, crs.getString(0)+" hello "+crs.getString(1)+"  12"+crs.getString(2));
        }
        crs.moveToFirst();
        return crs;
    }
    public Cursor userSearchShopByVsPlaceAndShop(String search1,String search2){
        SQLiteDatabase db = this.getReadableDatabase();
        String qry="select * from shops,visitingPlaces where shops.placeId=visitingPlaces.placeId and (PlaceName like '%"+search1+"' or PlaceName like '%"+search1+"%' "
                + "or PlaceName like '"+search1+"%' or city like '%"+search1+"'  or city like '%"+search1+"%' or city like '"+search1+"%' or PlaceType like '%"+search1+"'"
                + " or PlaceType like '%Shooping mall' or PlaceType like 'Shooping mall%' or location like '%Shooping mall' or location like '%"+search1+"%' "
                + "or location like '"+search1+"%') and (shopsname like '%"+search2+"' or shopsname like '%"+search2+"%' or shopsname like '"+search2+"%' "
                + "or ShopsCity like '%"+search2+"' or ShopsCity like '%"+search2+"%' or ShopsCity like '"+search2+"%' or ShopsArea like '%"+search2+"' "
                + "or ShopsArea like '%"+search2+"%' or ShopsArea like '"+search2+"%' or ShopsType like '%"+search2+"' or ShopsType like '%"+search2+"%' "
                + "or ShopsType like '"+search2+"%') ";
        Cursor crs=db.rawQuery(qry,null,null);
        crs.moveToFirst();
        while(crs.moveToNext()) {
            Log.e(TAG, crs.getString(0)+" hello "+crs.getString(1)+"  12"+crs.getString(2));
        }
        crs.moveToFirst();
        return crs;
    }

    public void updateShopRating(float Rate,int shopid) {
        SQLiteDatabase db = this.getReadableDatabase();
        String qryOldRating = "select ShopsRating from Shops where ShopsId=" + shopid;
        Cursor crsOldRating = db.rawQuery(qryOldRating, null, null);
        crsOldRating.moveToFirst();
        float oldRating = Float.parseFloat(crsOldRating.getString(0));

        String qryViewsCount = "select * from  Viewing where ShopsId=" + shopid;
        Cursor crsViewsCount = db.rawQuery(qryViewsCount, null, null);
        int count = crsViewsCount.getCount() - 1;

        float newRate = ((oldRating * count) + Rate) / (count + 1);

        SQLiteDatabase dbWrite = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ShopsRating", newRate);
        dbWrite.update("Shops", values, "ShopsId=?", new String[]{String.valueOf(shopid)});

    }
    public Cursor test(){
        SQLiteDatabase db = this.getReadableDatabase();
        String qryCount ="select * from  Viewing where ShopsId="+10;
        Log.e(TAG, qryCount+"  ");

        Cursor crsCount=db.rawQuery(qryCount,null,null);
        crsCount.moveToFirst();
        Log.e(TAG, crsCount+"  ");
        return crsCount;
    }
    public boolean userNotViewShopBefore(int userid,int shopid){
        SQLiteDatabase db = this.getReadableDatabase();
        String qry ="select * from Viewing where UserId="+userid+" and ShopsId ="+shopid;
        Log.e(TAG, qry+"  ");
        Cursor crs=db.rawQuery(qry,null,null);
        crs.moveToFirst();
        Log.e(TAG, crs.getCount()+"  ");
        return crs.getCount()==0;
    }

    public boolean isRecordNotSaveInInclude(int userid,int shopid){
        SQLiteDatabase db = this.getReadableDatabase();
        String qry ="select * from Includes where UserId="+userid+" and ShopsId ="+shopid;
        Log.e(TAG, qry+"  ");
        Cursor crs=db.rawQuery(qry,null,null);
        crs.moveToFirst();
        Log.e(TAG, crs.getCount()+"  ");
        return crs.getCount()==0;
    }
    public Cursor getUserIncludedShops(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String qry ="Create view if not EXISTS viewIncludedShopsUser As select * from Shops inner join Includes on Shops.ShopsId=Includes.ShopsId and Includes.Userid="+id;
        Log.d("MyTag", qry+"  ");
        Cursor crs=db.rawQuery(qry,null,null);
        crs.moveToFirst();
        String qry1="select * from viewIncludedShopsUser";
        Log.d("MyTag", qry1+"  ");
        Cursor crs1=db.rawQuery(qry1,null,null);
        crs1.moveToFirst();

//        while(crs.moveToNext()) {
//            Log.e(TAG, crs.getString(0)+" hello "+crs.getString(1)+"  12"+crs.getString(2));
//        }
        return crs1;
    }
    public void dropUserIncludedShopsView(){
        SQLiteDatabase db = this.getReadableDatabase();
        String qry="drop view IF EXISTS viewIncludedShopsUser";
        Cursor crs=db.rawQuery(qry,null,null);
        crs.moveToFirst();
        Log.e(TAG, " In drop include");

    }
    public String getShopAddress(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String qry ="select ShopsNum,ShopsStreet,PlaceId,ShopsArea,ShopsCity from shops where ShopsId="+id;
        Log.e(TAG, qry+"  ");
        Cursor crs=db.rawQuery(qry,null,null);
        crs.moveToFirst();
      String address="Shop# "+crs.getString(0)+" Street# "+crs.getString(1)+" \n"+crs.getString(3)+" "+
              getVisitingPlaceName(Integer.parseInt(crs.getString(2)))+" "+crs.getString(4);
        Log.e(TAG, address+"  ");
        return address;
    }

    public Cursor getShopComments(int id){
        SQLiteDatabase db = this.getReadableDatabase();
//        Log.e(TAG, " id "+id);
        String qry="Create view viewShopsComments As select Users.UserName,Viewing.comments,Viewing.ViewDate from Users INNER JOIN Viewing on Users.UserId=Viewing.UserId and ShopsId ="+id;
//        Log.e(TAG, " before create view");
        Cursor crs=db.rawQuery(qry,null,null);
        crs.moveToFirst();
//        Log.e(TAG, " After create view");
        String qry1 ="select * from viewShopsComments";
        Cursor crs1=db.rawQuery(qry1,null,null);
        crs1.moveToFirst();
        return crs1;
    }
    public void dropCommentsView(){
        SQLiteDatabase db = this.getReadableDatabase();
       String qry="drop view IF EXISTS viewShopsComments";
        Cursor crs=db.rawQuery(qry,null,null);
        crs.moveToFirst();
        Log.e(TAG, " In drop view");

    }

    public Cursor getUserShopComments(int id){
        SQLiteDatabase db = this.getReadableDatabase();
//        Log.e(TAG, " id "+id);
        String qry="Create view viewUserShopsComments As select Users.UserName,Viewing.comments,Viewing.ViewDate from Users INNER JOIN Viewing on Users.UserId=Viewing.UserId and ShopsId ="+id;
//        Log.e(TAG, " before create view");
        Cursor crs=db.rawQuery(qry,null,null);
        crs.moveToFirst();
//        Log.e(TAG, " After create view");
        String qry1 ="select * from viewUserShopsComments where comments !=''";
        Cursor crs1=db.rawQuery(qry1,null,null);
        crs1.moveToFirst();
        return crs1;
    }
    public void dropUserCommentsView(){
        SQLiteDatabase db = this.getReadableDatabase();
        String qry="drop view IF EXISTS viewUserShopsComments";
        Cursor crs=db.rawQuery(qry,null,null);
        crs.moveToFirst();
        Log.e(TAG, " In drop user view");

    }
    public Cursor searchShops(){
        SQLiteDatabase db = this.getReadableDatabase();
        String qry="select * from shops limit 10";
        Cursor crs=db.rawQuery(qry,null,null);
        crs.moveToFirst();
        return crs;
    }
    public void changeShopStreet(int id,String street){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ShopsStreet", street);
        db.update("Shops",values,"ShopsId=?",new String[]{String.valueOf(id)});
    }
    public void changeShopCity(int id,String city){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ShopsCity", city);
        db.update("Shops",values,"ShopsId=?",new String[]{String.valueOf(id)});
    }
    public void changeShopArea(int id,String area){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ShopsArea", area);
        db.update("Shops",values,"ShopsId=?",new String[]{String.valueOf(id)});
    }
    public void changeShopType(int id,String type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ShopsType", type);
        db.update("Shops",values,"ShopsId=?",new String[]{String.valueOf(id)});
    }
    public void changeShopvsPlace(int id,String vsPlace){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("PlaceId", Integer.parseInt(getVisitingPlaceRecord(vsPlace).getString(0)));
        db.update("Shops",values,"ShopsId=?",new String[]{String.valueOf(id)});
    }

    public void changeShopNumber(int id,String number){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ShopsNum", number);
        db.update("Shops",values,"ShopsId=?",new String[]{String.valueOf(id)});
    }
    public void changeShopName(int id,String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Shopsname", name);
        db.update("Shops",values,"ShopsId=?",new String[]{String.valueOf(id)});

    }
    public float getShopRating(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String qry="select ShopsRating from Shops where ShopsId="+id;
        Cursor crs =db.rawQuery(qry,null,null);
//
//        Log.e(TAG, qry+"  ");
//        while(crs.moveToNext()) {
////            Log.e(TAG, crs.getString(0)+" "+crs.getString(1)+" "+crs.getString(2)+" "+crs.getString(3)+" ");
////        }
        crs.moveToFirst();
        return Float.parseFloat(crs.getString(0));
    }
    public Cursor getShopKeeperShop(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String qry="select * from Shops where ShopkeeperId="+id;
        Cursor crs = db.rawQuery(qry,null,null);
        Log.e(TAG, qry+"  ");
        while(crs.moveToNext()) {
            Log.e(TAG, crs.getString(7)+" ");
        }
        crs.moveToFirst();

        return crs;
    }
    public Cursor isSessionExist(){
        SQLiteDatabase db = this.getReadableDatabase();
        String qry="select * from logInAccount";
        Cursor crs = db.rawQuery(qry,null,null);
        Log.d("MyTag", qry+"  ");
        crs.moveToFirst();
        return crs;
    }
    public String getShopKeeperName(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String qry="select * from ShopKeepers where ShopkeeperId="+id;
        Cursor crs = db.rawQuery(qry,null,null);
        Log.e(TAG, qry+"  ");
        while(crs.moveToNext()) {
            Log.e(TAG, crs.getString(0)+" ");
        }
        crs.moveToFirst();

        return crs.getString(1);
    }
    public String getUserName(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String qry="select * from Users where UserId="+id;
        Cursor crs = db.rawQuery(qry,null,null);
        Log.e(TAG, qry+"  ");
        while(crs.moveToNext()) {
            Log.e(TAG, crs.getString(0)+" ");
        }
        crs.moveToFirst();

        return crs.getString(1);
    }
    public boolean saveSession(String email, String password, String type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Email", email);
        values.put("Password", password);
        values.put("type", type);
        long res=db.insert("loginAccount", null, values);
        db.close();
        if(res==-1){
            return false;
        }
        else{
            return true;
        }
    }
    public boolean insertIntoShops(String ShopName, String ShopNum, String ShopStreet,String ShopCity, String ShopArea,String ShopType,int ShopkeeperId, int PlaceId) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Shopsname", ShopName);
        values.put("ShopsNum", ShopNum);
        values.put("ShopsStreet", ShopStreet);
        values.put("ShopsCity", ShopCity);
        values.put("ShopsArea", ShopArea);
        values.put("ShopsType", ShopType);
        values.put(" ShopkeeperId",  ShopkeeperId);
        values.put("PlaceId", PlaceId);
        long res=db.insert("Shops", null, values);
        db.close();
        if(res==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean insertCommentIntoViewing(int ShopsId, int UserId, String comments) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("ShopsId", ShopsId);
        values.put("UserId", UserId);
        values.put("comments", comments);
        long res=db.insert("Viewing", null, values);
        db.close();
        if(res==-1){
            return false;
        }
        else{
            return true;
        }
    }
public void updateCommentIntoViewing(int ShopsId, int UserId, String comments){
    SQLiteDatabase db = this.getWritableDatabase();
    Log.d("MyTag","updateCommentIntoViewing start ");
    ContentValues values = new ContentValues();
    values.put("comments", comments);
    db.update("Viewing",values,"ShopsId=? and UserId=?",new String[]{String.valueOf(ShopsId),String.valueOf(UserId)});
    Log.d("MyTag","updateCommentIntoViewing end ");
    }

    public boolean insertIntoIncludes(int UserId, int ShopsId) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("UserId", UserId);
        values.put("ShopsId", ShopsId);
        long res=db.insert("Includes", null, values);
        db.close();
        if(res==-1){
            return false;
        }
        else{
            return true;
        }
    }


    public boolean insertIntoVisitingPlaces(String PlaceName, String location, String City,String AboutPlace, String PlaceType) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("PlaceName", PlaceName);
        values.put("location", location);
        values.put("City", City);
        values.put("AboutPlace", AboutPlace);
        values.put("PlaceType", PlaceType);
        long res = db.insert("VisitingPlaces", null, values);
        db.close();
        if (res == -1) {
            return false;
        } else {
            return true;
        }
    }
    public boolean insertIntoShopkeeper(String shName, String shcellNo, String shEmail, String shPass) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ShopKeeperName", shName);
        cv.put("ShopKeeperCellNo", shcellNo);
        cv.put("ShopKeeperEmail", shEmail);
        cv.put("ShopKeeperPassword", shPass);
        long res=db.insert("ShopKeepers", null, cv);
        db.close();
        if(res==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean insertIntoUsers(String usName, String uscellNo, String usEmail, String usPass) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("UserName", usName);
        values.put("UserCellNo", uscellNo);
        values.put("UserEmail", usEmail);
        values.put("UserPassword", usPass);
        long res=db.insert("Users", null, values);
        db.close();
        if(res==-1){
            return false;
        }
        else{
            return true;
        }
            }

    public int getNumberofAcountsUser(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String qry = "select count(*) from Users where UserEmail='"+email+"'";
        Cursor crs = db.rawQuery(qry,null,null);
        Log.e(TAG, qry+"  ");
        Log.e(TAG, qry+"  ");
        while(crs.moveToNext()) {
            Log.e(TAG, crs.getString(0)+" ");
        }
        crs.moveToFirst();
        return Integer.parseInt(crs.getString(0)+"");
    }

    public int getNumberofAcountsShopKeeper(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String qry = "select count(*) from  ShopKeepers where ShopKeeperEmail='"+email+"'";
        Cursor crs = db.rawQuery(qry,null,null);
        Log.e(TAG, qry+"  ");
        Log.e(TAG, qry+"  ");
        while(crs.moveToNext()) {
            Log.e(TAG, crs.getString(0)+" ");
        }
        crs.moveToFirst();
        return Integer.parseInt(crs.getString(0)+"");
    }

    public Cursor getUserLoginEmailPass(String email,String pass){
        SQLiteDatabase db = this.getReadableDatabase();
        String qry="select * from Users where UserEmail='"+email+"' and UserPassword = '"+pass+"'";
        Cursor crs = db.rawQuery(qry,null,null);
        crs.moveToFirst();
        return crs;

    }
public void deleteSession(){
    SQLiteDatabase db = this.getWritableDatabase();
    db.delete("logInAccount",null,null);
}
    public Cursor getShopKeeperLoginEmailPass(String email,String pass){
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d("MyTag",email+" email password "+pass);
        String qry="select * from ShopKeepers where ShopKeeperEmail='"+email+"' and ShopKeeperPassword = '"+pass+"'";
        Cursor crs = db.rawQuery(qry,null,null);
        crs.moveToFirst();
        return crs;

    }
    public Cursor getUserLoginEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String qry="select * from Users where UserEmail='"+email+"'";
        Log.e(TAG, qry+"  ");
        Cursor crs = db.rawQuery(qry,null,null);
        while(crs.moveToNext()) {
            Log.e(TAG, crs.getString(0) + " " + crs.getString(1) + " " + crs.getString(2) + " " + crs.getString(3));
        }
        crs.moveToFirst();
        return crs;

    }
    public Cursor getShopKeeperLoginEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        String qry="select * from ShopKeepers where ShopKeeperEmail='"+email+"'";
        Cursor crs = db.rawQuery(qry,null,null);

        Log.e(TAG, qry+"  ");
        while(crs.moveToNext()) {
            Log.e(TAG, crs.getString(0) + " " + crs.getString(1) + " " + crs.getString(2) + " " + crs.getString(3));
        }

        crs.moveToFirst();
        return crs;

    }
    public Cursor getLoginid(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String qry="select * from Users where Userid="+id+"";
        Cursor crs = db.rawQuery(qry,null,null);

        Log.e(TAG, qry+"  ");
        while(crs.moveToNext()) {
            Log.e(TAG, crs.getString(0) + " " + crs.getString(1) + " " + crs.getString(2) + " " + crs.getString(3));
        }
        crs.moveToFirst();
        return crs;

    }
    public Cursor getVisitingPlaceRecord(String VPlace){
        SQLiteDatabase db = this.getReadableDatabase();
        String qry="select * from VisitingPlaces where PlaceName='"+VPlace+"'";
        Cursor crs = db.rawQuery(qry,null,null);
        Log.e(TAG, qry+"  ");
        while(crs.moveToNext()) {
            Log.e(TAG, crs.getString(0) + " " + crs.getString(1) + " " + crs.getString(2) + " " + crs.getString(3));
        }
        crs.moveToFirst();

        return crs;
    }


    public String getVisitingPlaceName(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String qry="select PlaceName from VisitingPlaces where PlaceId="+id;
        Cursor crs = db.rawQuery(qry,null,null);
        Log.e(TAG,qry+"");
        crs.moveToFirst();
//        Log.e(TAG,crs.getString(0)+"");
        return crs.getString(0);
    }
    public void changeUserPassword(int id,String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("UserPassword", pass);
        db.update("Users",values,"Userid=?",new String[]{String.valueOf(id)});

    }

    public void changeShopKeeperPassword(int id,String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ShopKeeperPassword", pass);
        db.update("ShopKeepers",values,"ShopKeeperid=?",new String[]{String.valueOf(id)});
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Users");
        db.execSQL("DROP TABLE IF EXISTS ShopKeepers");
        db.execSQL("DROP TABLE IF EXISTS VisitingPlaces");
        db.execSQL("DROP TABLE IF EXISTS Shops");
        db.execSQL("DROP TABLE IF EXISTS Includes");
        db.execSQL("DROP TABLE IF EXISTS Viewing");

        onCreate(db);
    }

}
