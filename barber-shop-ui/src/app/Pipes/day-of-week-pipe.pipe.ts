import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dayOfWeekPipe'
})
export class DayOfWeekPipePipe implements PipeTransform {

  transform(value: string): string {
    
    switch (value){
      case 'MONDAY':
        return 'Segunda-feira'
      case 'TUESDAY':
        return 'Terça-feira'
      case 'WEDNESDAY':
        return 'Quarta-feira'
      case 'THURSDAY':
        return 'Quinta-feira'
      case 'FRIDAY':
        return 'Sexta-feira'
      case 'SATURDAY':
        return 'Sábado'
      case 'SUNDAY':
        return 'Domingo'
      default:
        return 'dia não encontrado'
    }
    
  }

}
