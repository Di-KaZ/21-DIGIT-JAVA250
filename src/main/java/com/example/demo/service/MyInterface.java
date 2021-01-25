//package com.example.demo.service;
//
//public interface MailSender {
//    void envoyerMail(String to, String subject, String content);
//}
//
//public class MailSenderImplBidon implements MailSender {
//
//    @Override
//    public void envoyerMail(String to, String subject, String content) {
//        System.out.println("Tentative d'envoie de mail à " + to);
//    }
//}
//
//public class MailSenderSmtp implements MailSender {
//    @Override
//    public void envoyerMail(String to, String subject, String content) {
//        //try {
//        //            MimeMessage msg = new MimeMessage(session);
//        //            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
//        //            msg.addHeader("format", "flowed");
//        //            msg.addHeader("Content-Transfer-Encoding", "8bit");
//        //
//        //            msg.setFrom(new InternetAddress("no_reply@example.com", "NoReply-JD"));
//        //
//        //            msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));
//        //
//        //            msg.setSubject(subject, "UTF-8");
//        //
//        //            msg.setSentDate(new Date());
//        //
//        //            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
//        //
//        //            // Create the message body part
//        //            BodyPart messageBodyPart = new MimeBodyPart();
//        //
//        //            // Fill the message
//        //            messageBodyPart.setText(body);
//        //
//        //            // Create a multipart message for attachment
//        //            Multipart multipart = new MimeMultipart();
//        //
//        //            // Set text message part
//        //            multipart.addBodyPart(messageBodyPart);
//        //
//        //            // Second part is attachment
//        //            messageBodyPart = new MimeBodyPart();
//        //            String filename = "abc.txt";
//        //            DataSource source = new FileDataSource(filename);
//        //            messageBodyPart.setDataHandler(new DataHandler(source));
//        //            messageBodyPart.setFileName(filename);
//        //            multipart.addBodyPart(messageBodyPart);
//        //
//        //            // Send the complete message parts
//        //            msg.setContent(multipart);
//        //
//        //            // Send message
//        //            Transport.send(msg);
//        //            System.out.println("EMail Sent Successfully with attachment!!");
//        //        } catch (MessagingException e) {
//        //            e.printStackTrace();
//        //        } catch (UnsupportedEncodingException e) {
//        //            e.printStackTrace();
//        //        }
//    }
//}
//
//
//public class InscriptionService {
//    private MailSender mailSender;
//
//    public InscriptionService(MailSender mailSender) {
//        this.mailSender = mailSender;
//    }
//
//    public void registerNewUser(Object client) {
//        //... blabla
//        mailSender.envoyerMail("aaa@aa.com", "new user", " blabla");
//    }
//}
//
//public class PaiementService {
//    private MailSender mailSender;
//
//    public PaiementService(MailSender mailSender) {
//        this.mailSender = mailSender;
//    }
//
//    public void paiement(Object client) {
//        //... blabla
//        mailSender.envoyerMail("aaa@aa.com", "new user", " blabla");
//    }
//}
//
//
//    public static void main(String[] args) {
//        MailSender mailSender = null;
//        if (production)
//            mailSender = new MailSenderSmtp();
//        } else{
//            mailSender = new MailSenderImplBidon();
//        }
//        InscriptionService inscriptionService = new InscriptionService(mailSender);
//        PaiementService paiementService = new PaiementService(mailSender);
//    }