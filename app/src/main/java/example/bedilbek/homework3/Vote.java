package example.bedilbek.homework3;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

class Vote implements Parcelable, Serializable {
    private String firstName;
    private String lastName;
    private boolean agreement;
    private String food;
    private String drink;

    public Vote() {

    }

    public Vote(String firstName, String lastName, boolean agreement, String food, String drink) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.agreement = agreement;
        this.food = agreement ? food : "";
        this.drink = agreement ? drink : "";
    }

    public Vote(Parcel parcel) {
        this.firstName = parcel.readString();
        this.lastName = parcel.readString();
        this.agreement = parcel.readInt() == 1;
        this.food = agreement ? parcel.readString() : "";
        this.drink = agreement ? parcel.readString() : "";
    }

    public static final Creator<Vote> CREATOR = new Creator<Vote>() {
        @Override
        public Vote createFromParcel(Parcel in) {
            return new Vote(in);
        }

        @Override
        public Vote[] newArray(int size) {
            return new Vote[size];
        }
    };

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isAgreement() {
        return agreement;
    }

    public String getFood() {
        return food;
    }

    public String getDrink() {
        return drink;
    }

    @Override
    public String toString() {
        return agreement
                ? String.format("%s %s will come to the party and wants %s and %s", firstName, lastName, food, drink)
                : String.format("%s %s will not come to the party", firstName, lastName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeInt(agreement ? 1 : 0);
        if (agreement) {
            dest.writeString(food);
            dest.writeString(drink);
        }
    }

//    private void writeObject(java.io.ObjectOutputStream out) {
//        try {
//            out.writeChars(firstName);
//            out.writeChars(lastName);
//            out.writeInt(agreement ? 1 : 0);
//            if (agreement) {
//             out.writeChars(food);
//             out.writeChars(drink);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.exit(-1);
//        }
//
//    }

//    private void readObject(java.io.ObjectInputStream in) {
//        try {
//            this.firstName = in.readUTF();
//            this.lastName = in.readUTF();
//            this.agreement = in.readInt() == 1;
//            this.food = agreement ? in.readUTF() : "";
//            this.drink = agreement ? in.readUTF() : "";
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.exit(-1);
//        }
//    }
}
