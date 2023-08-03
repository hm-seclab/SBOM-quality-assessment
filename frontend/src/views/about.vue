<template>

  <div class="container">
    <br/>
    <p-card class="surface-100">
      <template #title>
        About
      </template>
      <template #content>
        <p>This Webpage is part of a masterthesis at the German University of Applied Sciences Munich (HM). The website makes part of the generated data publiclly availible.</p>
        <p>The topic of the thesis is <b>Quality assessment of SBOM generation Tools and Standards on Open Source projects</b></p>
      </template>
    </p-card>

    <br/>

    <p-card class="surface-100">
      <template #title>
        Abstract
      </template>
      <template #content>
        <p>
        The availability of freely accessible open source software has become one of the fundamental driving forces behind innovation in software development. Nowadays, a vast majority of systems are built on some form of open source software. This may include entire application frameworks, such as the Java Jakarta framework or the Spring framework. It also applies to application servers like Nginx or Apache servers that offer to host self-developed software. And this fact also reaches down to the basic OS layer. Prominantly, the Linux kernel and lots of operating systems based on the linux kernel are open source and widly used. And also on a fundamental level basic standards and protocolls like HTTP or IP are open source. It can be said that open source is an inevitable building block for the majority of services.
        </p>
        <p>
        While open source software offers numerous advantages, it also presents a potential attack surface for adversaries. The supply chain for a product based on open source software can become extremely complex, particularly when the system is based on a mixed ecosystem without an existing package repository infrastructure. Nowadays, it is common for larger projects to be composed of several technologies, such as C or C++ for performance or driver implementations, Java or Python for actual system logic, and a frontend system like a PHP application server or a JavaScript framework like React, Vue.js, or Angular. Additionally, bigger projects are often composed for a wide variety of platforms, such as Windows and Linux, or different architectures like 32 or 64-bit systems, or x86 or ARM processor architecture. Moreover, there are different virtualization systems like Docker, Kubernetes, or Clouds that get served with their customized builds. All of this complexity makes it challenging to keep track of versions and affected products.
        </p>
        <p>
        Given this complexity, there is a clear need to keep track of it, especially as more and more vulnerabilities are found in open source projects. The Log4j vulnerability is a case in point. This vulnerability was based on a simple expression language tag, which would be interpreted by the logging library if such a statement was logged. An attacker could use this to load code from a remote source and execute it to achieve remote code execution. Log4j is a widely used library in the Java ecosystem. Finding and replacing this vulnerable library in all affected products was a prime example of the need to track the used products and their dependencies in an infrastructure.
        </p>
        <p>
        The American government's Executive Order 14028 (EO) has further catalyzed this debate. In the EO, the White House addressed the need for enhancing software supply chain security. To achieve this, software suppliers for the American federal government will be required to provide a Software Bill of Materials (SBOM) for their products. This will enable software users to keep track of all the components contained in a product and ensure that they are up to date or if there are recently discovered vulnerabilities in a software product. In addition, the National Institute of Standards and Technology (NIST), National Telecommunications and Information Administration (NTIA), and Cybersecurity and Infrastructure Security Agency (CISA) have published requirements and guidance for the generation and use of an SBOM.
        </p>
      </template>
    </p-card>

    <br/>

    <p-card class="surface-100">
      <template #title>
        Contact
      </template>
      <template #content>
        <p>This masterthesis is conducted by Marius Biebel <a href="mailto:mbiebel@hm.edu">mbiebel[at]hm.edu</a>.</p>
        <p>The thesis is supervised by Prof. Dr. Peter Trapp and Prof. Dr. Erik Krempel.</p>
        <p>The infrastrucure is provided by the Seclab at the HM. <a href="https://seclab.cs.hm.edu/" target="_blank">https://seclab.cs.hm.edu/</a></p>
        <p>Seclab Imprint: <a href="https://seclab.cs.hm.edu/imprint/" target="_blank">here</a></p>
        <p>HM Imprint: <a href="https://www.hm.edu/en/impressum_1/legal_notice_1.en.html" target="_blank">here</a></p>
      </template>
    </p-card>
  </div>
</template>

<script>

</script>

<style scoped>

</style>