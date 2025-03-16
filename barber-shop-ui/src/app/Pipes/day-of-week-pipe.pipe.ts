import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dayOfWeekPipe'
})
export class DayOfWeekPipePipe implements PipeTransform {

  transform(value: string): string {
    
    switch (value){
      case 'SEGUNDA':
        return 'Segunda-feira'
      case 'TERCA':
        return 'Terça-feira'
      case 'QUARTA':
        return 'Quarta-feira'
      case 'QUINTA':
        return 'Quinta-feira'
      case 'SEXTA':
        return 'Sexta-feira'
      case 'SABADO':
        return 'Sábado'
      case 'DOMINGO':
        return 'Domingo'
      default:
        return 'dia não encontrado'
    }
    
  }

}
