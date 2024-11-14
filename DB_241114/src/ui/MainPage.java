package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPage extends JFrame {
    public MainPage(String username) {  // 사용자 이름을 인자로 받음
        setTitle("메인페이지");
        setSize(1060, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 화면 가운데에 창 배치

        // 메인 패널 설정
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // 사용자 환영 메시지 설정
        JLabel userLabel = new JLabel(username + "님, 환영합니다!");
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 버튼 생성 및 이벤트 추가
        JButton bookRegisterButton = new JButton("도서등록");
        JButton myLibraryButton = new JButton("나의 대여목록");
        JButton cartButton = new JButton("장바구니");
        JButton logoutButton = new JButton("로그아웃");

        // 버튼의 크기 통일
        Dimension buttonSize = new Dimension(200, 40);
        bookRegisterButton.setMaximumSize(buttonSize);
        myLibraryButton.setMaximumSize(buttonSize);
        cartButton.setMaximumSize(buttonSize);
        logoutButton.setMaximumSize(buttonSize);

        // 각 버튼에 대한 클릭 이벤트 설정
        bookRegisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "도서등록 페이지로 이동합니다.");
            }
        });

        myLibraryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "나의 미디어목록 페이지로 이동합니다.");
            }
        });

        cartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "장바구니 페이지로 이동합니다.");
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "로그아웃 되었습니다.");
            }
        });

        // 패널에 컴포넌트 추가
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(userLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(bookRegisterButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(myLibraryButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(cartButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(logoutButton);

        // 프레임에 패널 추가
        add(mainPanel);

        setVisible(true);
    }

    public static void main(String[] args) {
        // 로그인된 사용자의 이름을 예시로 전달 ("홍길동"으로 가정)
        String loggedInUsername = "nameLabel"; 
        new MainPage(loggedInUsername);
    }
}
