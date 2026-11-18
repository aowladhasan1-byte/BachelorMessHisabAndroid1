# ব্যাচেলর মেস হিসাব — Android অ্যাপ

এই প্রজেক্টে একটি অফলাইন Android WebView অ্যাপ রয়েছে। সদস্য, বাজার খরচ, দৈনিক মিল এবং টাকা জমার তথ্য ফোনের লোকাল স্টোরেজে রাখা হয়। অ্যাপ চালাতে ইন্টারনেট লাগে না এবং কোনো ইন্টারনেট পারমিশনও চাওয়া হয় না।

## অ্যাপের ফিচার
- সদস্য যোগ, নিষ্ক্রিয়/সক্রিয় করা
- তারিখ, বাজারের বিবরণ, খরচ ও কে টাকা দিয়েছেন—এসব সংরক্ষণ
- সদস্যভিত্তিক সকালের, দুপুরের ও রাতের মিল এন্ট্রি
- সদস্যের টাকা জমা সংরক্ষণ
- মাস নির্বাচন করে মোট বাজার, মোট মিল, মিল রেট এবং সদস্যভিত্তিক বাকি/অগ্রিম দেখা
- JSON ব্যাকআপ ডাউনলোড ও ইমপোর্ট

## Android Studio দিয়ে APK তৈরি
1. ZIP Extract করুন।
2. Android Studio-তে `BachelorMessHisabAndroid` ফোল্ডারটি Open করুন।
3. JDK 17, Android SDK Platform 35 এবং Android Build Tools 35 ইনস্টল থাকতে হবে।
4. Gradle Sync শেষ হলে **Build → Build Bundle(s) / APK(s) → Build APK(s)** নির্বাচন করুন।
5. APK তৈরি হবে: `app/build/outputs/apk/debug/app-debug.apk`

কমান্ড: `./gradlew assembleDebug` (Windows: `gradlew.bat assembleDebug`)। প্রথমবার Gradle ডাউনলোডের জন্য ইন্টারনেট প্রয়োজন।

## GitHub Actions দিয়ে APK তৈরি
প্রজেক্টটি GitHub repository-তে `main` branch-এ push করলে অথবা Actions থেকে **Build Android APK → Run workflow** চালালে APK artifact তৈরি হবে। Workflow-এর `Bachelor-Mess-Hisab-APK` artifact ডাউনলোড করুন।

## গুরুত্বপূর্ণ
- ডেটা শুধু এই ফোনে থাকে; অন্য ফোনে নিজে থেকে সিঙ্ক হয় না।
- App data মুছে গেলে লোকাল হিসাব হারাতে পারেন; নিয়মিত JSON ব্যাকআপ রাখুন।
- এটি Debug APK; Play Store প্রকাশের জন্য আলাদা release signing প্রয়োজন।
