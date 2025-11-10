import { Component, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-nav-bar-component',
  standalone: true,
  imports: [],
  templateUrl: './nav-bar-component.component.html',
  styleUrl: './nav-bar-component.component.scss'
})
export class NavBarComponentComponent {
  @Output() toggleSidebar = new EventEmitter<void>();
}