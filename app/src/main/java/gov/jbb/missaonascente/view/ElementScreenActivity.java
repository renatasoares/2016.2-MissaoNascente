package gov.jbb.missaonascente.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import gov.jbb.missaonascente.R;
import gov.jbb.missaonascente.controller.ElementsController;
import gov.jbb.missaonascente.controller.LoginController;
import gov.jbb.missaonascente.controller.RegisterElementController;
import gov.jbb.missaonascente.model.Element;

public class ElementScreenActivity extends AppCompatActivity {
    private ImageView elementImage;
    private TextView elementsName;
    private TextView elementsDescription;
    private TextView catchDate;

    private static final String TAG = "ElementScreenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element_screen);
        initViews();
        Intent elementIntent = getIntent();

        LoginController loginController = new LoginController();
            loginController.loadFile(this);
        ElementsController elementsController = new ElementsController();
        int idElement = elementIntent.getIntExtra("idElement", 0);


        Element touchedElement;
        touchedElement = elementsController.findElementByID(idElement, loginController.getExplorer().getEmail(),this.getApplicationContext());

        String path = RegisterElementController.findImagePathByAssociation(this, idElement);

        Bitmap image = null;

        if(path.equals("")){
            image = BitmapFactory.decodeResource(getResources(), idElement);
        }

        if(image == null){
            image = RegisterElementController.loadImageFromStorage(path, this);
        }

        Drawable drawable = RoundedBitmapDrawableFactory.create(getResources(), image);
        elementImage.setImageDrawable(drawable);
        this.elementsName.setText(touchedElement.getNameElement());
        this.elementsDescription.setText(touchedElement.getTextDescription());
        String catchDate =  this.catchDate.getText() + ": " + touchedElement.getCatchDate();
        this.catchDate.setText(catchDate);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent almanacScreenIntent = new Intent(ElementScreenActivity.this, AlmanacScreenActivity.class);
        ElementScreenActivity.this.startActivity(almanacScreenIntent);
        finish();
    }

    private void initViews(){
        this.elementImage = (ImageView) findViewById(R.id.elementImage);
        this.elementsName = (TextView) findViewById(R.id.elementsName);
        this.elementsDescription = (TextView)findViewById(R.id.elementsDescription);
        this.catchDate = (TextView) findViewById(R.id.catchDate);
    }
}
