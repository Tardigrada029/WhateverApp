# specific image which is used as base image
FROM openjdk:11
# app's labels (metadata)
LABEL org.opencontainers.image.title="WhateverApp" \
      org.opencontainers.image.description="WhateverApp build with Gradle" \
      org.opencontainers.image.authors="@tardigrada029"
# create directory in container image for app code
RUN mkdir -p /home/app
# copy app code (.) to /home/app in container image
COPY src/main/kotlin/com/tardigrada/WhateverApp /home/app
COPY build/libs/WhateverApp-0.0.1-SNAPSHOT.jar /home/app
# set working directory context
WORKDIR /home/app
# command for container to execute
ENTRYPOINT ["java", "-jar","WhateverApp-0.0.1-SNAPSHOT.jar"]