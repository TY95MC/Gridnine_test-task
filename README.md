Задача: написать модуль, который фильтрует входящий набор перелетов согласно различным правилам. Правила могут выбираться и задаваться динамически в зависимости от контекста выполнения операции фильтрации.   
Получить тестовый набор данных нужно методом FlightBuilder.createFlights().    
Исключите из тестовых данных перелеты по следующим правилам(по каждому правилу нужен отдельный вывод списка перелетов в консоль):
- вылет до текущего момента времени
- имеются сегменты с датой прилета раньше даты вылета
- общее время, проведенное на земле, превышает 2 часа(время на земле - интервал между прилетом одного сегмента и вылетом следующего за ним) 