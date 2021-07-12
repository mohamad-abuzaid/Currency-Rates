**Before Creating a new PR, you should check the following**

1. Make sure your branch builds with no issues
2. Make sure all UnitTests run with no issues
3. Make sure your branch runs with no issues
4. Resolve all warnings (example)
	1. Unused functions/parameters
	2. Unnecessary Null checks
	3. Unused imports
	4. ....
5. Remove all commented unused code
6. Don't keep hard-coded Strings
6. Your code is formatted correctly following "SquareAndroid"
7. Follow naming rules:
	1. xml layout --> fragment_**_**
	2. xml shapes --> shape_**_**
	3. drawables icons --> ic_**_**
	4. drawables vectors --> ic_svg_**_**
	5. adapter items --> item_**_**
	6. views ids:
		1. TextView --> tv_**_**
		2. EditText --> et_**_**
		3. RecyclerView --> rv_**_**
		4. ImageView --> iv_**_**