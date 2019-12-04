glGetUniformLocation - Возвращает местоположение переменной
glGetAttribLocation - Возвращает местоположение переменной

glUniform4f - вызов glUniform4f, который задает цвет

glVertexAttribPointer -  сообщаем системе, что шейдеру для своего атрибута a_Position необходимо читать данные из массива vertexData.
                            А параметры этого метода позволяют подробно задать правила чтения.

glVertexAttribPointer(aPositionLocation, 2, GL_FLOAT, false, 0, vertexData)
                            int indx – переменная указывающая на положение атрибута в шейдере.
                            Тут все понятно, используем ранее полученную aPositionLocation, которая знает где сидит a_Position.
                            int size – указывает системе, сколько элементов буфера vertexData брать для заполнения атрибута a_Position.
                            int type – передаем GL_FLOAT, т.к. у нас float значения

                            boolean normalized – этот флаг нам пока неактуален, ставим false
                            int stride – используется при передаче более чем одного атрибута в массиве.
                            Мы пока передаем данные только для одного атрибута, поэтому пока ставим 0. Но в последующих уроках мы еще используем этот параметр.
                            Buffer ptr – буффер с данными, т.е. vertexData.
