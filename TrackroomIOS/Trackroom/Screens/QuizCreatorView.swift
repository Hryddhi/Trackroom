//
//  QuizCreatorView.swift
//  Trackroom
//
//  Created by Rifatul Islam on 9/4/22.
//

import SwiftUI
import Alamofire

struct QuizCreatorView: View {
    @State var NoOfQuestionSelection: Int = 1

    //@State var quizData = [CreateQuiz]()
    @State var quizData: QuizData = QuizData(title: "Sample Title", description: "Sample Description", startTime: "St Time", endTime: "End Time", quizContent: [QuizContent(question: "Quiestion", option: ["A","B","C","D"], correctAnswer: "A")])
    
    
    

    
//    @State var quizQuestion: String = ""
//    @State var quizOptionA: String = ""
//    @State var quizOptionB: String = ""
//    @State var quizOptionC: String = ""
//    @State var quizOptionD: String =  ""
//    @State var quizCorrectOptionSelection: [String] = ["","","","",""]
//
    @State var quizCorrectOption: [String] = ["A", "B", "C", "D"]

    var body: some View {
        ZStack(alignment: .top){
            Color("BgColor")
                .edgesIgnoringSafeArea(.all)
            
            ScrollView {
                
                HStack {
                    Text("Quiz Creator")
                        .font(.title3)
                        .fontWeight(.bold)
                        .padding(.top)
                        .padding(.leading, 32)
                    
                    Spacer()
                    
                    Text("Submit Quiz")
                        .font(.title3)
                        .fontWeight(.bold)
                        .foregroundColor(Color("PrimaryColor"))
                        .padding(.top)
                        .padding(.trailing, 32)
                        .onTapGesture {
                            print(quizData)
                            createNewQuiz()
                        }
                    
                }
                
                //Call CreateQuiz Function
                ForEach(0..<NoOfQuestionSelection, id: \.self) { i in
                    VStack {
                    
                        CustomQuizTextField(textFieldInput: $quizData.quizContent[i].question, textFieldLabel: "Type Question...", iconName: "questionmark.app")
                        CustomQuizTextField(textFieldInput: $quizData.quizContent[i].option[0], textFieldLabel: "Type First Option...", iconName: "a.square")
                        CustomQuizTextField(textFieldInput: $quizData.quizContent[i].option[1], textFieldLabel: "Type Second Option...", iconName: "b.square")
                        CustomQuizTextField(textFieldInput: $quizData.quizContent[i].option[2], textFieldLabel: "Type Third Option...", iconName: "c.square")
                        CustomQuizTextField(textFieldInput: $quizData.quizContent[i].option[3], textFieldLabel: "Type Forth Option...", iconName: "d.square")
                        //CustomQuizTextField(textFieldInput: $quizData.quizContent[i].correctAnswer, textFieldLabel: "Type Correct Answer...", iconName: "chevron.right.2")
                        
                        HStack {
                            Text("Correct Answer")
                                .fontWeight(.bold)
                            
                            Spacer()
                            
                            
                            Picker(selection: $quizData.quizContent[i].correctAnswer, content: {
                                ForEach(quizCorrectOption, id: \.self) {result in
                                    Text(result)
                                        .foregroundColor(Color.white)
                                        .fontWeight(.bold)
                                }
                            }, label: {
                                HStack {
                                    Text(quizData.quizContent[i].correctAnswer)
                                }
                            })
                                .frame(width: 75, height: 30)
                                .foregroundColor(Color.white)
                                .padding(.horizontal, 32)
                                .background(Color("GreyColor"))
                                .cornerRadius(10)
                        }
                        .padding(.horizontal, 32)
                        .padding(.vertical, 8)
                        
                        
                        CustomDivider()
                        
                    }
                }
                
                Text("Add Question")
                    .fontWeight(.bold)
                    .foregroundColor(Color("PrimaryColor"))
                    .padding(.top, 8)
                    .onTapGesture {
                        initQuizArray()
                    }
                
                
            }
            .padding(.top, 16)
        }
    }
    
    func createNewQuiz() {
        
        let access = UserDefaults.standard.string(forKey: "access")
        let headers: HTTPHeaders = [.authorization(bearerToken: access!)]
        
        AF.request(CHANGE_PASSWORD,
                   method: .post,
                   parameters: quizData,
                   encoder: JSONParameterEncoder.default,
                   headers: headers).response { response in
            
            let status = response.response?.statusCode
            print("Create New Quiz Status : \(status)")
        }
    }
    
    func initQuizArray() {
        NoOfQuestionSelection += 1
        quizData.quizContent.append(QuizContent(question: "Quiestion", option: ["A","B","C","D"], correctAnswer: "A"))
    }
    
}

struct QuizCreatorView_Previews: PreviewProvider {
    static var previews: some View {
        QuizCreatorView()
    }
}
