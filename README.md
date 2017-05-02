# Android-Project-App-Image-Saver
Image Saver is essentially, a category separated image library application. It works different from say the “Android Gallery” in that it only loads the images from saved URLs that the user enters. The main screen has two sets of functionality: 
1)	The top set of radio buttons are used to display the number of URLs that are saved, and in which category (queries a local SQLite database).
2)	The bottom allows you to delete all the URLs from the categories you select using the checkboxes.
The user can click “Explore Images”, which will bring them to the selection screen. Here the user can select which category of saved images they wish to view. Upon clicking a category, the app will load the images that have been saved (from the URLs) and display them for the user. The user can then delete the individual images, or expand them for further viewing.
The expanded view gives a few options for the user to play with. They can rotate the screen to get a much fuller view of the image, display the URL (where the image is located), or download it to their respective device. It’s important to remember that the images aren’t actually saved on the phone, and are only loaded from the URL. This option listed gives the user an actual copy of the image that will be saved to their device (accomplishes this with the download manager). A notification (and local link) is shown upon success, and toast is displayed upon failure (example: a dead URL).
The final functionality worth mentioning is the actual ability to add new images. From the selection screen the user simply needs to hit “Add Image”, which will populate a dialog to the screen. On this screen the user can select the category their image will appear in and type the URL to which it will be coming from. Once the user agrees to add it, the app will verify the URL, and either display the image or put a placeholder in the case of a dead/fake URL.
Note: The refresh button is used to reload the images should there be temporary connection issues.
Note: For more information on the application, hit the “App Information” button on the main screen.

# Different Files and Modifications
Android Manifest
1)	Modified permissions to allow specialized Internet access, and file storage access
2)	Changed icon image to my own created icon
3)	Performed an override on App Theme for my own personal style change 
Java Files
1)	MainActivity -> Functionality for the home page of the application (separate activity)
2)	SelectionScreen -> Functionality for the second screen that allows one to add photos
3)	CategoryFragment -> Functionality for the list fragment that appears on the Selection Screen
4)	DialogFrag -> Functionality for the dialog that pops up for adding a new photo
5)	ExpandImage -> Functionality for activity that appears when a photo is opened (expanded view)
Drawable
1)	badlink.jpg -> Image used as a placeholder in the case of a bad URL
2)	border.xml -> Places a black border around selected layouts or sections
3)	buttonstyle.xml -> Changes the style of buttons 
Layout
1)	activity_expand_image.xml -> An activity that shows an expanded version of a selected image (landscape and portrait view)
2)	activity_main.xml -> An activity for the home page of the application (landscape and portrait view)
3)	activity_selection_screen.xml -> The secondary screen activity that shows the pictures and holds the list fragment (landscape and portrait view)
4)	category_list_fragment -> Replica of the simple list used in the fragment class (replaces it for creating the category list)
5)	fagment_dialog.xml -> Framgment dialog used to add an image URL to the database and thefore the app
6)	image_inflator.xml -> Inflator used for when a new image is added to the app display on the selection screen





Minimap
1)	ic_launcher.png -> Changed the icon for the app
Values
1)	color.xml -> Created my own custom colours for the application
2)	trings.xml -> Contains string-array for the category fragment, and the app_info string for when the the app information dialog is called
3)	styles.xml -> Contains custom styles for buttons, and other aspects of the app
Gradle Scripts
1)	app -> contains dependency for picasso

