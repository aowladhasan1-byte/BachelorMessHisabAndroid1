# মোবাইল থেকে APK তৈরি করার সহজ নিয়ম

এই পরিবেশে Android SDK নেই এবং Gradle ডাউনলোডের নেটওয়ার্ক সংযোগও পাওয়া যাচ্ছে না। তাই APK এখানে সরাসরি compile করা যায়নি। GitHub Actions-এর মাধ্যমে বিনামূল্যে build করা যাবে।

## ধাপ ১ — GitHub repository তৈরি
1. https://github.com খুলে নিজের অ্যাকাউন্টে Sign in করুন।
2. **New repository** চাপুন। নাম দিন `BachelorMessHisabBuild`।
3. Repository **Public** বা **Private** যেকোনোটি হতে পারে। **Create repository** চাপুন।

## ধাপ ২ — ZIP আপলোড
1. নতুন repository-তে **Add file → Upload files** চাপুন।
2. এই প্রজেক্টের `BachelorMessHisabAndroid.zip` ফাইলটি বেছে নিন।
3. **Commit changes** চাপুন।

## ধাপ ৩ — Build workflow যোগ
1. Repository-তে **Add file → Create new file** চাপুন।
2. File name-এ হুবহু লিখুন: `.github/workflows/build-apk.yml`
3. ChatGPT থেকে দেওয়া `build-apk.yml` ফাইলের সব লেখা কপি করে এডিটরে পেস্ট করুন।
4. **Commit changes** চাপুন।

## ধাপ ৪ — APK build
1. Repository-র **Actions** ট্যাবে যান।
2. **Build Bachelor Mess Hisab APK** workflow নির্বাচন করুন।
3. **Run workflow → Run workflow** চাপুন।
4. Build শেষ হলে সবুজ ✓ দেখা যাবে।
5. ওই run-এর পেজের নিচে **Artifacts** থেকে `Bachelor-Mess-Hisab-APK` ডাউনলোড করুন।
6. ZIP extract করলে `app-debug.apk` পাবেন। সেটি Android ফোনে খুলে Install করুন।

> APK ইনস্টল করতে ফোনে প্রয়োজন হলে Files/Chrome-এর জন্য **Install unknown apps** অনুমতি দিন।
