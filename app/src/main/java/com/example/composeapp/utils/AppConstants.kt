package com.example.composeapp.utils

object AppConstants {

    object TripType {
        const val SPIRITUAL = 1
        const val HONEY_MOON = 2
        const val GROUP = 3
        const val SOLO = 4
        const val ROAD_TRIP = 5
        const val BIKE_TRIP = 6
    }

    object TripDescription {
        const val SPIRITUAL = "Spiritual travel"
        const val HONEY_MOON = "Honey moon"
        const val GROUP = "Group trip"
        const val SOLO = "Solo trip"
        const val ROAD_TRIP = "Road trip"
        const val BIKE_TRIP = "Bike trip"
    }

    object SourcePage {
        const val ParamType = "sourcePage"
        const val SRC_SEARCH = "search_page"
        const val SRC_LISTING = "listing_page"
    }

    object EditField {
        const val NAME = "NAME"
        const val EMAIL = "EMAIL"
        const val LOCATION = "LOCATION"
        const val PASSWORD = "PASSWORD"
    }

    object SearchParam {
        const val GPT_MODEL = "gpt-3.5-turbo"
    }

    const val TYPE_TERMS = 1
    const val TYPE_POLICY = 2
    const val TERMS = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "<meta charset=\"UTF-8\">\n" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "<title>Travel Planning App - Terms and Conditions</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "<h1>Terms and Conditions</h1>\n" +
            "\n" +
            "<h2>1. Introduction</h2>\n" +
            "<p>Welcome to PlanMeGPTrip Travel Planning Application. These Terms and Conditions govern your use of the App. By accessing or using the App, you agree to be bound by these Terms and Conditions.</p>\n" +
            "\n" +
            "<h2>2. User Accounts</h2>\n" +
            "<p>You may be required to create an account to use certain features of the App. You are responsible for maintaining the confidentiality of your account credentials and for all activities that occur under your account.</p>\n" +
            "\n" +
            "<h2>3. Content</h2>\n" +
            "<p>The App may allow you to upload, submit, store, send, or receive content. You retain ownership of any intellectual property rights that you hold in that content. By uploading, submitting, storing, sending, or receiving content, you grant us a worldwide, non-exclusive, royalty-free, transferable license to use, reproduce, distribute, prepare derivative works of, display, and perform that content in connection with the provision of the App.</p>\n" +
            "\n" +
            "<h2>4. Privacy</h2>\n" +
            "<p>Your privacy is important to us. Please refer to our Privacy Policy for information on how we collect, use, and disclose your personal information.</p>\n" +
            "\n" +
            "<h2>5. Limitation of Liability</h2>\n" +
            "<p>The App is provided on an \"as is\" and \"as available\" basis, without any warranties of any kind, either express or implied. We shall not be liable for any direct, indirect, incidental, special, consequential, or exemplary damages arising out of or in connection with the use of the App.</p>\n" +
            "\n" +
            "<h2>6. Governing Law</h2>\n" +
            "<p>These Terms and Conditions shall be governed by and construed in accordance with the laws of India, without regard to its conflict of law provisions.</p>\n" +
            "\n" +
            "<h2>7. Changes to Terms and Conditions</h2>\n" +
            "<p>We reserve the right to modify or replace these Terms and Conditions at any time. It is your responsibility to review these Terms and Conditions periodically for changes. Your continued use of the App following the posting of any changes to these Terms and Conditions constitutes acceptance of those changes.</p>\n" +
            "\n" +
            "<h2>8. Contact Us</h2>\n" +
            "<p>If you have any questions about these Terms and Conditions, please contact us at planmegptrip@gmail.com.</p>\n" +
            "\n" +
            "</body>\n" +
            "</html>\n"
    const val PRIVACY = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "<meta charset=\"UTF-8\">\n" +
            "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "<title>PlanMeGPTrip - Privacy Policy</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "<h1>Privacy Policy</h1>\n" +
            "\n" +
            "<p>This Privacy Policy describes how your personal information is collected, used, and shared when you use our Travel Planning Application (PlanMeGPTrip).</p>\n" +
            "\n" +
            "<h2>1. Information We Collect</h2>\n" +
            "<p>We collect information you provide directly to us when you use the App. This may include:</p>\n" +
            "<ul>\n" +
            "  <li>Information you provide when creating an account, such as your name, email address, and password.</li>\n" +
            "  <li>Information you provide when using the App's features, such as travel preferences, itinerary details, and location data.</li>\n" +
            "</ul>\n" +
            "\n" +
            "<h2>2. How We Use Your Information</h2>\n" +
            "<p>We use the information we collect to:</p>\n" +
            "<ul>\n" +
            "  <li>Provide and maintain the App;</li>\n" +
            "  <li>Personalize your experience and tailor content and offers to your interests;</li>\n" +
            "  <li>Communicate with you, including responding to your inquiries and providing customer support;</li>\n" +
            "  <li>Analyze usage of the App and improve our services;</li>\n" +
            "  <li>Comply with legal obligations.</li>\n" +
            "</ul>\n" +
            "\n" +
            "<h2>3. Sharing Your Information</h2>\n" +
            "<p>We may share your information with third parties in the following circumstances:</p>\n" +
            "<ul>\n" +
            "  <li>With service providers who help us operate the App and provide services;</li>\n" +
            "  <li>With your consent or at your direction;</li>\n" +
            "  <li>In response to a legal request, such as a subpoena, court order, or government demand;</li>\n" +
            "  <li>To protect the rights, property, or safety of the App, our users, or others.</li>\n" +
            "</ul>\n" +
            "\n" +
            "<h2>4. Data Retention</h2>\n" +
            "<p>We will retain your information for as long as necessary to fulfill the purposes outlined in this Privacy Policy unless a longer retention period is required or permitted by law.</p>\n" +
            "\n" +
            "<h2>5. Security</h2>\n" +
            "<p>We take reasonable measures to protect your information from unauthorized access, disclosure, alteration, or destruction.</p>\n" +
            "\n" +
            "<h2>6. Children's Privacy</h2>\n" +
            "<p>The App is not intended for use by children under the age of 13. We do not knowingly collect personal information from children under 13. If you are a parent or guardian and believe that your child has provided us with personal information, please contact us.</p>\n" +
            "\n" +
            "<h2>7. Changes to Privacy Policy</h2>\n" +
            "<p>We may update this Privacy Policy from time to time to reflect changes in our practices or for other operational, legal, or regulatory reasons. We will notify you of any material changes by posting the new Privacy Policy on this page.</p>\n" +
            "\n" +
            "<h2>8. Contact Us</h2>\n" +
            "<p>If you have any questions about this Privacy Policy, please contact us at planmegptrip@gmail.com.</p>\n" +
            "\n" +
            "</body>\n" +
            "</html>\n"
}
