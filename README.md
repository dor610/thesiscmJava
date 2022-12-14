# Cách chạy chương trình
## _Yêu cầu_
* _Khuyến khích sử dụng IDE intellij IDEA, có thể sử dụng Eclipse thay thế_
* _Java 11_
* _Cổng 8080 trống_
* _IDE đã cài đặt Gradle_
## _Cách chạy_
1. Mở file application.properties trong thư mục resources và chạy lện gradle build để download các dependencies 
2. Chọn run project tuỳ vào IDE

## _Thông tin thêm_
Ứng dụng đang sử dụng các dịch vụ bên thứ 3 như MongoDB Atlas đối với cơ sở dữ liệu, Dropbox đối với dịch vụ lưu trữ file và MailGun cho dịch vụ gửi email
Nếu trong quá trình khởi động xuất hiện lỗi liên quan đến các dịch vụ trên thì có thể access token đến các dịch vụ đa hết hạn. Vui lòng thay thế và chạy lại ứng dụng.
 * _MongoDb Atlas được config trong resources/application.properties_
 * _Dropbox được config trong utils/DropboxUtils.java_
 * _Mailgun được config trong service/implement/MailServiceImpl.java_
 
Để có thể quan sát dữ liệu của ứng dụng có thể dùng ứng dụng MongoDBCompass để kết nối đển MongoDB Atlas.
Connection string được lưu trữ trong file resources/application.properties
