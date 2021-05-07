import { Component, OnInit } from '@angular/core';
import { UserService, User } from '@workspace/core-data';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  data: any = [
    {icon: "work", title:"Projects", link: "/projects",
    line1: "Create a project of your choice"},
    
    {icon: "help", title:"Questions", link: "/questions",
    line1: "Create the questions of your choice"},

    {icon: "dashboard", title:"Quiz", link: "/quiz",
    line1: "Collect the questions and design a quiz"},
    
    {icon: "question_answer", title:"Exam", link: '/exam',
    line1: "Take exam and analyse results"},
  ]

  currUser: User;

  constructor(private userService: UserService, private router: Router) {
    this.userService.getUserDetails()
      .subscribe(res => {
        this.currUser = res;
      })
  }

  ngOnInit(): void {
  }

  goToPage(pageName:string){
    this.router.navigate([`${pageName}`]);
  }

}
