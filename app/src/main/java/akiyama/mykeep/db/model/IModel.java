package akiyama.mykeep.db.model;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Parcelable;

/**
 *
 * @author zhiwu_yan
 * @version 1.0
 * @since 2015-07-03  17:30
 */
public interface IModel extends Parcelable {

    public abstract ContentValues values();

    public abstract Uri getContentUri();

    public abstract String getTable();
}